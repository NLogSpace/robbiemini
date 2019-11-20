package de.leifaktor.robbiemini;

import de.leifaktor.robbiemini.actor.Player;

public class TestEpisode {

	public static Episode createTestEpisode() {
		Episode epi = new Episode();
		epi.roomManager = new RoomManager();
		epi.globalVars = new GlobalVars();
		epi.startingRoom = new XYZPos(0,3,1);
		epi.player = new Player(3, 3, 0);
		
		Room room111 = RoomCreator.createShiftRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room112 = RoomCreator.createMazeRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room121 = RoomCreator.createEmptyTestRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room122 = RoomCreator.createWallRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room101 = RoomCreator.createMagneticRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room102 = RoomCreator.createBridgeRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room103 = RoomCreator.createPSpaceRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		epi.roomManager.setRoom(1, 1, 1, room111);
		epi.roomManager.setRoom(1, 1, 2, room112);
		epi.roomManager.setRoom(1, 2, 1, room121);
		epi.roomManager.setRoom(1, 2, 2, room122);
		epi.roomManager.setRoom(1, 0, 1, room101);
		epi.roomManager.setRoom(1, 0, 2, room102);
		epi.roomManager.setRoom(1, 0, 3, room103);
		
		return epi;
	}
	
}
