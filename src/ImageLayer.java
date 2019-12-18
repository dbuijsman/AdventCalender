import java.util.ArrayList;

public class ImageLayer {
    private Integer[][] pixels;
    private ImageLayer nextLayer;
    ImageLayer(ArrayList<Integer[][]> layers){
        this.pixels = layers.remove(0);
        if(layers.size()>0) {
            this.nextLayer = new ImageLayer(layers);
        }
    }
    public int[] getPixelsSize(){
        return new int[]{pixels.length, pixels[0].length};
    }
    public int getVisiblePixel(int row, int column){
        if(this.pixels[row][column]==2 && nextLayer!=null){
            return nextLayer.getVisiblePixel(row, column);
        } else {
            return this.pixels[row][column];
        }
    }

    public int getAmountDigit(int digit){
        int count = 0;
        for(Integer[] row : pixels){
            for(Integer pixel : row){
                if(pixel==digit){
                    count++;
                }
            }
        }
        return count;
    }
    ImageLayer getLayerWithMinimal(int digit){
        if(nextLayer==null){
            return this;
        } else {
            ImageLayer layer = nextLayer.getLayerWithMinimal(digit);
            return (layer.getAmountDigit(digit)<this.getAmountDigit(digit)) ? layer : this;
        }
    }
}
