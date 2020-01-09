import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    enum Direction {NORTH, EAST, SOUTH, WEST};
    private String name;
    private boolean mayEnter;
    private boolean isExplored;
    private boolean isCheckpoint;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private HashMap< Direction, Room> neighbours = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Item> originalItems = new HashMap<>();
    Room(String name){
        this.name = name;
        mayEnter = !name.equals("Wall");
        isExplored = name.equals("Wall");
        isCheckpoint = name.equals("Checkpoint");
    }
    public boolean isExplored(){
        return isExplored;
    }

    public boolean isCheckpoint() {
        return isCheckpoint;
    }
    public int getUnknownNeighbours(){
        return 4-neighbours.values().size();
    }
    public String getName() {
        return name;
    }
    public Room getNeighbour(Direction direct){
        return neighbours.getOrDefault(direct, null);
    }
    public void addWalls(ArrayList<Direction> doors){
        for(Direction direction : Direction.values()){
            if(!doors.contains(direction)){
                neighbours.put(direction, new Room("Wall"));
            }
        }
    }

    public boolean isMayEnter(){
        return mayEnter;
    }
    public void addNeighbour(Direction direction, Room lastRoom){
        if(!neighbours.containsKey(direction)){
            neighbours.put(direction, lastRoom);
        }
    }
    public void addItem(String itemName){
        items.put(itemName, new Item(itemName));
        originalItems.put(itemName, new Item(itemName));
    }
    public void checkExplored(){
        if(isExplored){
            return;
        }
        for(Item item : items.values()){
            if(!item.isChecked()){
                return;
            }
        }
        int unexplored = 0;
        for(Direction direct : Direction.values()){
            if(!neighbours.containsKey(direct) || !neighbours.get(direct).isExplored()){
                unexplored++;
                if(unexplored>1){
                    return;
                }
            }
        }
        isExplored = true;
    }
    public String getNextCommand(Direction lastDirection){
        for(Item item : items.values()){
            if(!item.isChecked()){
                return "take " + item.getName();
            }
        }
        for(Item item : items.values()){
            if(item.mayPickUp()){
                return "take " + item.getName();
            }
        }
        for(Direction direct : Direction.values()){
            if(!neighbours.containsKey(direct)){
                return direct.name().toLowerCase();
            }
        }
        for(Direction direct : Direction.values()){
            if(!neighbours.get(direct).isExplored){
                if(lastDirection!=null && direct == getOpposite(lastDirection)){
                    continue;
                }
                return direct.name().toLowerCase();
            }
        }
        for(Direction direct : Direction.values()){
            if(neighbours.get(direct).isMayEnter()){
                return direct.name().toLowerCase();
            }
        }
        return "";
    }
    public Item take(String name){
        Item item = items.get(name);
        items.remove(name);
        item.take();
        return item;
    }
    public void drop(Item item){
        items.put(item.getName(), item);
        item.drop();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\n");
        sb.append("Doors: \n");
        for(Direction direct : Direction.values()){
            sb.append("- " + direct.name() + ": ");
            sb.append(neighbours.containsKey(direct) ? neighbours.get(direct).getName() : "??????" );
            sb.append("\n");
        }
        sb.append("\n");
        if(items.keySet().size()!=0){
            sb.append("Items: \n");
            for(Item item : items.values()){
                sb.append("- " + item + "\n");
            }
        }
        sb.append("Explored: " + isExplored + "\n");
        return sb.toString();
    }
    public void reset(){
        items = new HashMap<>();
        isExplored = false;
        for(Item item : originalItems.values()){
            item.drop();
            items.put(item.getName(),item);
        }
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
}
