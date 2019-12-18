import java.util.ArrayList;
import java.util.Arrays;

public class Day8ImageFormat {
    public static void main(String[] args){
        Image image = importImage();
        ImageLayer layer = image.getLayerWithMinimal(0);
        System.out.println("The answer is " + (layer.getAmountDigit(1)*layer.getAmountDigit(2)));
        image.printFinalImage();

    }
    private static Image importImage(){
        ArrayList<Integer[][]> layers = new ArrayList<>();
        String[] input = General.importResource("Day8 Image");
        for(String line: input){
            char[] pixels = line.toCharArray();
            int index=0;
            while(index+25*6 <= pixels.length){
                Integer[][] pixelsLayer = new Integer[6][25];
                for(int indexRow=0;indexRow<6;indexRow++){
                    for(int indexColumn=0;indexColumn<25;indexColumn++){
                        pixelsLayer[indexRow][indexColumn] = pixels[index] - '0';
                        index++;
                    }
                }
                layers.add(pixelsLayer);
            }
        }
        return new Image(layers);
    }
}
