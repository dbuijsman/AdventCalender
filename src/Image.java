import java.util.ArrayList;
import java.util.Arrays;

public class Image {
    private ImageLayer topLayer;
    Image(ArrayList<Integer[][]> layers){
        this.topLayer = new ImageLayer(layers);
    }
    public ImageLayer getLayerWithMinimal(int digit){
        return topLayer.getLayerWithMinimal(digit);
    }
    public int[][] getFinalImage(){
        int[] imageSize = topLayer.getPixelsSize();
        int[][] finalImage = new int[imageSize[0]][imageSize[1]];
        for(int row=0;row<imageSize[0];row++){
            for(int column=0;column<imageSize[1];column++){
                finalImage[row][column] = topLayer.getVisiblePixel(row,column);
            }
        }
        return finalImage;
    }
    public void printFinalImage(){
        int[][] image = getFinalImage();
        char[] charRow = new char[image[0].length];
        for(int[] row: image){
            for(int index=0;index<charRow.length;index++){
                charRow[index] = (row[index]==1) ? '\u2588' : '\u0000';
            }
            System.out.println(Arrays.toString(charRow).replace("[","").replace("]","").replace(", ",""));
        }
    }
}
