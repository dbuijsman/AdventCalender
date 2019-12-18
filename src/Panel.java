public class Panel {
    private int color;
    boolean isColored;
    Panel(int color){
        if(color<0 || color>1){
            throw new IllegalArgumentException("Color must be 0 or 1.");
        }
        this.color = color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getColor() {
        return color;
    }
}
