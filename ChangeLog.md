# PA04 CHANGE LOG
- Added the ProxyController class that implements the BattleSalvoController interface to handle interaction with the server
- Changed the Coord constructor to only take in ints x and y as parameters for easier conversion with the CoordJson object
- Added the ShipAdapter class to convert a Ship object to a ShipJson object
- Added the RandomShipGen class to randomly place ships onto the game board more accurately, both vertically and horizontally
- Changed CompPlayer and CompData classes to AiPlayer and AiData respectively to maintain consistency
- Added the edge case where a player cannot repeat shots on coordinates
- Changed the numShots method in the PlayerData abstract class to consider the edge case that the player has more shots than available coordinates left on their opponent’s board.
- Added the getData method in the AiPlayer class in order to test the ProxyController’s randomness factor
- Added the setBoard method in the PlayerData class for smoother interaction in the ProxyController class
- Added the ShipDirection enumeration to match the server requests
- Added the GameType enumeration to match the server requests
- Added Json messages and objects to communicate data with the server