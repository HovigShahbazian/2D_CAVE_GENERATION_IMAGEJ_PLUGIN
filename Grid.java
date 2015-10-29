import java.util.ArrayList;


public class Grid {
    private int height;
    private int width;
    private Node[][] nodeArray ;


    public Grid(){
        width = 0;
        height = 0;
    }

    public Grid(int w,int h){
        width = w;
        height = h;
        nodeArray = new Node[width][height];

        for(int i =0; i <width;i++){
                nodeArray[i] = new Node[height];
                for(int j =0; j<height;j++) {
                    nodeArray[i][j] = new Node(false);
                }
        }


    }

    public Node[][] getGrid() {
        return nodeArray;
    }

    public Node getNode(int x,int y){
        return nodeArray[x][y];
    }

    public ArrayList<Node> getNeighbors(Node node){
       ArrayList<Node> neighbors = new ArrayList<Node>();

        for (int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++){
                if(x == 0 && y == 0)
                    continue;


                int checkX = node.getX() + x+1;
                int checkY = node.getY() + y+1;

                if(checkX >= 0 && checkX < width && checkY < height){
                    neighbors.add(nodeArray[checkX][checkY]);
                }

            }
        }
       return neighbors;
    }


    public int getHeight() {
        return  height;
    }

    public int getWidth(){
        return width;
    }
}
