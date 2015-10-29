
public class Node{
    boolean isWall = false;
    int gridX = 0;
    int gridY = 0;

    public Node(boolean b){
        isWall = b;
    }

    public boolean getWall(){
        return isWall;
    }

    public void setWall(boolean w){
        isWall = w;
    }

    public void setX(int x){
        gridX = x;
    }

    public int getX(){
        return gridX;
    }


    public void setY(int y){
        gridY = y;
    }

    public int getY(){
        return gridY;
    }

}
