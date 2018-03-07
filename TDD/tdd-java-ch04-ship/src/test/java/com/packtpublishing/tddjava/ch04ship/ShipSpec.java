package com.packtpublishing.tddjava.ch04ship;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.testng.Assert.*;

@Test
public class ShipSpec {

    private static final int INITIAL_POINT_X = 2;
    private static final int INITIAL_POINT_Y = 4;
    private static final Direction INITIAL_DIRECTION = Direction.NORTH;
    private static final String COMMANDS = "rflb";
    private static final int MAX_X = 50;
    private static final int MAX_Y = 50;

    private Ship ship;
    private Location location;
    private Planet planet;

    @BeforeMethod
    public void initShip(){
        Point point = new Point(INITIAL_POINT_X, INITIAL_POINT_Y);
        this.location = new Location(point, INITIAL_DIRECTION);
        Point maxPoint = new Point(MAX_X, MAX_Y);
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(2, 1));
        obstacles.add(new Point(2, 4));
        this.planet = new Planet(point,obstacles);
        ship = new Ship(location, planet);
    }

    public void whenInstantiateShipThenLocationIsSet(){

        assertEquals(ship.getLocation(), this.location);
    }

    public void whenForwardThenShipMoveForward(){
        Location expectedLocation = this.location.copy();
        expectedLocation.forward(planet.getMax(), planet.getObstacles());
        ship.moveForward();
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenBackwardThenShipMoveBackward(){
        Location expectedLocation = this.location.copy();
        expectedLocation.backward(planet.getMax(), planet.getObstacles());
        ship.moveBackward();
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenLeftThenTurnLeft(){
        Direction initialDirection = ship.getLocation().getDirection();
        ship.turnLeft();
        assertNotEquals(ship.getLocation().getDirection(), initialDirection);
    }

    public void whenRightThenTurnRight(){
        Direction initialDirection = ship.getLocation().getDirection();
        ship.turnRight();
        assertNotEquals(ship.getLocation().getDirection(), initialDirection);
    }

    public void whenFCommandThenMoveForward(){
        Location expectedLocation = this.location.copy();
        expectedLocation.forward(planet.getMax(), planet.getObstacles());
        ship.moveCommand("f");
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenBCommandThenMoveBackwards(){
        Location expectedLocation = this.location.copy();
        expectedLocation.backward(this.planet.getMax(), this.planet.getObstacles());
        ship.moveCommand("b");
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenLCommandThenTurnLeft(){
        Location expectedLocation = this.location.copy();
        expectedLocation.turnLeft();
        ship.moveCommand("l");
        assertEquals(ship.getLocation(), expectedLocation);
    }
    public void whenRCommandThenTurnRight(){
        Location expectedLocation = location.copy();
        expectedLocation.turnRight();
        ship.moveCommand("r");
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenRecieveCommandsStringThenExecuteOneByOne(){
        Location expectedLocation = location.copy();
        expectedLocation.turnRight();
        expectedLocation.forward(planet.getMax(), planet.getObstacles());
        expectedLocation.turnLeft();
        expectedLocation.backward(planet.getMax(), planet.getObstacles());
        ship.moveCommand(COMMANDS);
        assertEquals(ship.getLocation(), expectedLocation);
    }

   public void whenInstantiateShipThenAddPlanet(){
        assertEquals(ship.getPlanet(), planet);
   }

   public void whenShipPassEastBoundaryThenWrapShipPositionToWest(){
       location.setDirection(Direction.EAST);
       location.getPoint().setX(planet.getMax().getX());
       ship.moveCommand("f");
       assertEquals(location.getX(), 1);
   }

    public void whenShipPassWestBoundaryThenWrapShipPositionToEast(){
        location.setDirection(Direction.EAST);
        location.getPoint().setX(1);
        ship.moveCommand("b");
        assertEquals(location.getX(), planet.getMax().getX());
    }

    public void whenForwardObstacleThenShipStays(){
        Location expectedLocation = location.copy();
        expectedLocation.forward(planet.getMax(), planet.getObstacles());
        ship.moveCommand("f");
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenBackwardObstacleThenShipStays(){
        Location expectedLocation = location.copy();
        expectedLocation.backward(planet.getMax(), planet.getObstacles());
        ship.moveCommand("b");
        assertEquals(ship.getLocation(), expectedLocation);
    }

    public void whenCommandsExecutedThenGenerateReport(){
        String report = "";
        Location expectedLocation = location.copy();
        expectedLocation.turnRight();
        report += "O";
        report += expectedLocation.forward(planet.getMax(), planet.getObstacles()) ? "O" : "X";
        expectedLocation.turnLeft();
        report +="O";
        report += expectedLocation.backward(planet.getMax(), planet.getObstacles()) ? "O" : "X";
        String result = ship.moveCommand(COMMANDS);
        assertEquals(result, report);
    }

}
