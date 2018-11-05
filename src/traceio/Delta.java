package src.traceio;

public class Delta {

    private int scanRange; // how many pixels to look at
    //private int scanThreshold; // if greater than this, it's significant

    public Delta(int range, double threshold) {

        this.scanRange = range;
        //this.scanThreshold = threshold;
    }
    

    public int average(int[][] array, int y, int x) {

        int middle = array[y][x];
        int starty = y - scanRange;
        int endy = y + scanRange;
        int startx = x - scanRange;
        int endx = x + scanRange;
        int average_alpha = 0;
        int average_red = 0;
        int average_green = 0;
        int average_blue = 0;


        if (starty < 0) { starty = 0; }
        if (endy > array.length) { endy = array.length; }
        if (startx < 0) { startx = 0; }
        if (endx > array[0].length) { endx = array[0].length; }

        // top 
        for (int r = starty; r < endy; r++) {
            for (int c = startx; c < endx; c++) {

                average_alpha += (array[r][c] & 0xFF000000) >> 24;
                average_red += (array[r][c] & 0x00FF0000) >> 16;
                average_green += (array[r][c] & 0x0000FF00) >> 8;
                average_blue += (array[r][c] & 0x000000FF);
            }
        }

        average_alpha /= ((endy - starty) * (endx - startx));
        average_red /= ((endy - starty) * (endx - startx));
        average_green /= ((endy - starty) * (endx - startx));
        average_blue /= ((endy - starty) * (endx - startx));

        return average_alpha << 24
             | average_red << 16
             | average_green << 8
             | average_blue;      
    }




}