import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ShipDroid {
    private ASCII ascii;
    private ArrayList<Item> inventory = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    private  ArrayList<Item> droppedItems = new ArrayList<>();
    private HashMap<String, Room> rooms = new HashMap<>();
    private ArrayList<Room.Direction> directions = new ArrayList<>();
    ShipDroid(ASCII computer){
        this.ascii = computer;
        for(Room.Direction direction : Room.Direction.values()){
            directions.add(direction);
        }
    }
    void run(){
        String inp = null;
        do{
            inventory = new ArrayList<>();
            for(Room room : rooms.values()){
                room.reset();
            }
            ArrayList<String> output = new ArrayList<>();
            ascii.reset();
            ascii.run(new ArrayList<>());
            Room currentRoom = null;
            Room nextRoom = null;
            boolean tookItem = false;
            Room.Direction newDirection = null;
            LOOP: while(!ascii.isFinished()){
                if(!tookItem) {
                    output = ascii.outputToStringArray();
                    String nameRoom = null;
                    boolean foundRoomName = false;
                    boolean foundDoors = false;
                    boolean foundItems = false;
                    ArrayList< Room.Direction > doors = new ArrayList<>();
                    ArrayList< String > items = new ArrayList<>();
                    CHECK_LINES: for (String line : output) {
                        if(line.contains("You can't go that way.")){
                            print();
                            ArrayList<String> neww = new ArrayList<>();
                            neww.add("west");
                            newDirection = Room.Direction.WEST;
                            currentRoom = rooms.get("== Security Checkpoint ==");
                            nextRoom = currentRoom.getNeighbour(newDirection);
                            ascii.run(neww);
                            continue LOOP;
                        }
                        if (line.contains("==")) {
                            nameRoom = line;
                            foundRoomName = true;
                            continue;
                        }
                        if (line.contains("Doors here lead:")) {
                            foundDoors = true;
                            continue;
                        }
                        if (foundDoors) {
                            if (line.length() == 0) {
                                foundDoors = false;
                                continue;
                            }
                            for (Room.Direction direction : Room.Direction.values()) {
                                if (line.contains(direction.name().toLowerCase())) {
                                    doors.add(direction);
                                    continue CHECK_LINES;
                                }
                            }
                            throw new IllegalStateException("Can't read properly: " + line);
                        }
                        if (line.contains("Items here:")) {
                            foundItems = true;
                            continue;
                        }
                        if (foundItems) {
                            if (line.length() == 0) {
                                foundItems = false;
                                continue;
                            }
                            items.add(line.substring(2));
                        }
                    }
                    if (!foundRoomName) {
                        break;
                    }
                    if (doors.size()==0) {
                        ascii.run(new ArrayList<>());
                        output = ascii.outputToStringArray();
                        CHECK_LINES: for (String line : output) {
                            if (line.contains("Doors here lead:")) {
                                foundDoors = true;
                                continue;
                            }
                            if (foundDoors) {
                                if (line.length() == 0) {
                                    foundDoors = false;
                                    continue;
                                }
                                for (Room.Direction direction : Room.Direction.values()) {
                                    if (line.contains(direction.name().toLowerCase())) {
                                        doors.add(direction);
                                        continue CHECK_LINES;
                                    }
                                }
                                throw new IllegalStateException("Can't read properly: " + line);
                            }
                            if (line.contains("Items here:")) {
                                foundItems = true;
                                continue;
                            }
                            if (foundItems) {
                                if (line.length() == 0) {
                                    foundItems = false;
                                    continue;
                                }
                                items.add(line.substring(2));
                            }
                        }
                    }
                    if (nextRoom == null) {
                        if(rooms.containsKey(nameRoom)){
                            nextRoom = rooms.get(nameRoom);
                        } else {
                            nextRoom = new Room(nameRoom);
                            nextRoom.addWalls(doors);
                            for (String item : items) {
                                nextRoom.addItem(item);
                            }
                            rooms.put(nameRoom, nextRoom);
                        }
                    }
                    if (currentRoom != null && newDirection!=null) {
                        if(nameRoom.equals(currentRoom.getName())){
                            currentRoom.addNeighbour(newDirection, new Room("Checkpoint"));
                        } else {
                            currentRoom.addNeighbour(newDirection, nextRoom);
                            nextRoom.addNeighbour(getOpposite(newDirection), currentRoom);
                        }
                    }
                    currentRoom = nextRoom;
                    currentRoom.checkExplored();
                    nextRoom = null;
                }
                if(inventory.size()==8 && currentRoom.getName().contains("Security Checkpoint")){
                    break LOOP;
                }
                ArrayList<String> commands = new ArrayList<>();
                String newCommand = currentRoom.getNextCommand(newDirection);
                commands.add(newCommand);
                if(newCommand.startsWith("take")){
                    tookItem = true;
                } else {
                    if(newCommand!=""){
                        newDirection = Room.Direction.valueOf(newCommand.toUpperCase());
                        nextRoom = currentRoom.getNeighbour(newDirection);
                    } else {
                        newDirection = null;
                    }
                    tookItem = false;
                }
                ascii.run(commands);
                if(tookItem){
                    Item item = currentRoom.take(newCommand.substring(5));
                    item.checked();
                    if(ascii.isFinished()){
                        item.makeLethal();
                        inp = "deadeee";
                    } else {
                        inventory.add(item);
                        allItems.add(item);
                    }

                }
            }
            if(inp != null && inp.equals("deadeee")){
                inp = "yes";
            } else if(inventory.size()==8 && currentRoom.getName().contains("Security Checkpoint")){
                tryCheckpoint();
                return;
            }
        } while(inp.startsWith("y"));
    }

    String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine();
        return ans;
    }
    Room.Direction getOpposite(Room.Direction direction){
        switch (direction){
            case NORTH:
                return Room.Direction.SOUTH;
            case WEST:
                return Room.Direction.EAST;
            case SOUTH:
                return Room.Direction.NORTH;
            case EAST:
                return Room.Direction.WEST;
        }
        return null;
    }
    public void print(){
        for(Room room : rooms.values()){
            System.out.println(room);
        }
        System.out.println("Inventory:");
        for(Item item : inventory){
            System.out.println(item);
        }
        int countItems = 0;
        for(Item item : allItems){
            if(item.mayPickUp() && !inventory.contains(item)){
                System.out.println("We missed the item: " + item.getName());
                countItems++;
            }
        }
        System.out.println("Found " + inventory.size() + " items of the " + (inventory.size()+countItems) + " known non-lethal items.");
        int countRooms = 0;
        for(Room room : rooms.values()){
            countRooms += room.getUnknownNeighbours();
        }
        System.out.println("There are " + countRooms + " unknown doors.");
    }
    public void tryCheckpoint(){
        droppedItems = new ArrayList<>();
        String weight = checkWeight();
        while(!weight.equals("perfect weigth")){
            ArrayList<String> input = new ArrayList<>();
            if(weight.equals("heavier")){
                Item itemToTake = droppedItems.get(new Random().nextInt(droppedItems.size()));
                droppedItems.remove(itemToTake);
                inventory.add(itemToTake);
                input.add("take " + itemToTake.getName());
            } else {
                Item itemToDrop = inventory.get(new Random().nextInt(inventory.size()));
                inventory.remove(itemToDrop);
                droppedItems.add(itemToDrop);
                input.add("drop " + itemToDrop.getName());
            }
            ascii.run(input);
            weight = checkWeight();
        }
    }
    public String checkWeight(){
        ArrayList<String> input = new ArrayList<>();
        input.add("west");
        ascii.run(input);
        ArrayList<String> output = ascii.outputToStringArray();
        for(String line : output){
            if(line.contains("heavier")){
                return "heavier";
            }
            if(line.contains("lighter")){
                return "lighter";
            }
        }
        for(String line : output){
            System.out.println(line);
        }
        return "perfect weigth";
    }
}
