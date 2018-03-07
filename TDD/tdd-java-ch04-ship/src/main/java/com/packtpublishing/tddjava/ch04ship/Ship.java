package com.packtpublishing.tddjava.ch04ship;

public class Ship {

    private final Location location;
    private Planet planet;

    public Ship(Location location, Planet planet) {
        this.location = location;
        this.planet = planet;
    }

    public Location getLocation() {
        return this.location;
    }

    public Planet getPlanet() {
        return planet;
    }

    public boolean moveForward(){
        return this.location.forward(this.planet.getMax(), this.planet.getObstacles());
    }
    public boolean moveBackward(){
        return this.location.backward(this.planet.getMax(), this.planet.getObstacles());
    }
    public void turnLeft(){
        this.location.turnLeft();
    }

    public void turnRight(){
        this.location.turnRight();
    }

    public String moveCommand(String moveDirection){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <moveDirection.length() ; i++) {
            String command = Character.toString(moveDirection.charAt(i));
            result.append(executeCommands(command));
        }
        return result.toString();
    }

    private String executeCommands(String command){
        String result = "O";
        switch(command.toLowerCase()){
            case "f": result = moveForward() ? "O" : "X"; break;
            case "b": result = moveBackward() ? "O" : "X"; break;
            case "l": turnLeft(); break;
            case "r": turnRight(); break;
            default:
                break;
        }
        return result;
    }
}
