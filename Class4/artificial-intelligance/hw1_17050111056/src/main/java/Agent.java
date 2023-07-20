
import java.util.Random;

/**
 *
 * @author elif
 */
public class Agent {

    int[] location = new int[2]; //random
    int numberOfAction = 0;
    Environment e = new Environment(); //agent need environment information

    public Agent(Environment e) {
        this.e = e;
    }
    public Agent(){
        
    }

    public String choseAction() {
        String action;
        Random r = new Random();
        String[] actions = {"Go Right", "Go Left", "Go Up", "Go Down"};
        action = actions[r.nextInt(actions.length)];
        return action;
    }

    public int[] generateStartingPosition() {
        int[] location = new int[2];
        int range = e.size;
        location[0] = (int) (Math.random() * range);
        location[1] = (int) (Math.random() * range);
        return location;
    }

    public void clean(int location[]) {
        if (e.matrix[location[0]][location[1]] == 1) {
            e.matrix[location[0]][location[1]] = 0;
        }
    }    
    
    //agent clean and print environment
    public void agentAction(int location[]) {
        clean(location);
        e.printMatrix(location);
        System.out.println("--------------------------");
    }

    public Object goRight(int location[]) {
        if (location[1] + 1 >= e.size) {
            System.out.println("Border! Take Another Action");
            return "Border";
        } else {
            location[1]++;
            return location;
        }
    }

    public Object goLeft(int location[]) {
        if (location[1] - 1 < 0) {
            System.out.println("Border! Take Another Action");
            return "Border";
        } else {
            location[1]--;
            return location;
        }
    }

    public Object goUp(int location[]) {
        if (location[0] - 1 < 0) {
            System.out.println("Border! Take Another Action");
            return "Border";
        } else {
            location[0]--;
            return location;
        }
    }

    public Object goDown(int location[]) {
        if (location[0] + 1 >= e.size) {
            System.out.println("Border! Take Another Action");
            return "Border";
        } else {
            location[0]++;
            return location;
        }
    }

    public void beginCleaning() {
        int[] location = generateStartingPosition();
        System.out.println("Starting location:" + location[0] + "-" + location[1]);
        System.out.println("Starting Number of dirty areas " + e.numberofDirtyPlaces());
        agentAction(location);
        String action;
        while (true) {
            action = choseAction();
            //if all places are clean, finish cleaning
            if (e.checkClean()) {
                break;
            }
            if ("Go Right".equals(action)) {
                if (goRight(location) != "Border") {
                    agentAction(location);
                } else {
                    action = choseAction();
                }
            } else if ("Go Left".equals(action)) {
                if (goLeft(location) != "Border") {
                    agentAction(location);
                } else {
                    action = choseAction();
                }
            } else if ("Go Up".equals(action)) {
                if (goUp(location) != "Border") {
                    agentAction(location);
                } else {
                    action = choseAction();
                }
            } else if ("Go Down".equals(action)) {
                if (goDown(location) != "Border") {
                    agentAction(location);
                } else {
                    action = choseAction();
                }
            }
            numberOfAction++;
        }
        System.out.println("Number of action is " + numberOfAction);
    }
}
