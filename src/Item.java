public class Item {
    private String name;
    private boolean isChecked;
    private boolean isLethal;
    private boolean inInventory;
    Item(String name){
        this.name = name;
        isLethal = false;
        isChecked = false;
        inInventory = false;
        if(name.equals("infinite loop") || name.equals("giant electromagnet")){
            isLethal = true;
            isChecked = true;
        }
    }

    public String getName() {
        return name;
    }

    public boolean mayPickUp(){
        return !isLethal;
    }
    public void makeLethal(){
        isLethal = true;
    }
    public boolean isChecked(){
        return isChecked;
    }

    public boolean isInInventory() {
        return inInventory;
    }
    public void take(){
        inInventory = true;
    }
    public void drop(){
        inInventory = false;
    }
    public void checked(){
        this.isChecked =true;
    }
    public String toString(){
        return name + " lethal: " + isLethal + " checked: " + isChecked;
    }
}
