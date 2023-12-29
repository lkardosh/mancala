package mancala;


public class KalahRules extends GameRules{

     private boolean aBonus=false;

     public KalahRules() {
      super();
    }
     
      @Override
      int captureStones(final int stoppingPoint){
        final int numCaptured=getDataStructure().removeStones(13-stoppingPoint);
        if (numCaptured==0){
          return 0;
        }
        getDataStructure().removeStones(stoppingPoint);
        if(stoppingPoint <=6){
          getDataStructure().addToStore(1, numCaptured+1);
        }else if(stoppingPoint>6 && stoppingPoint<13){
          getDataStructure().addToStore(2, numCaptured+1);
        }
        return numCaptured;
    } 

     private void validateMoveStones(final int startPit, final int playerNum)throws InvalidMoveException {
          if(startPit<1||startPit>12){
          throw new InvalidMoveException("Starting pit does not exist");
          }
          if((playerNum==1) && startPit>6){
          throw new InvalidMoveException("Selected pit is not on Player One's side");
          }
          if((playerNum==2) && startPit<7){
          throw new InvalidMoveException("Selected pit is not on Player Two's side");
          }
     }

    @Override
    public int moveStones(final int startPit, final int playerNum)throws InvalidMoveException {
        validateMoveStones(startPit, playerNum);
        setPlayer(playerNum); 
        final int originalNumStones=getDataStructure().getStoreCount(playerNum);
        distributeStones(startPit);
          //determine amount put in stores
        final int newNumStones=getDataStructure().getStoreCount(playerNum);
        return newNumStones-originalNumStones;       
     }

      private void switchPlayers(){
        if(getCurrentPlayer()==1){
          setPlayer(2);
        }
        else if(getCurrentPlayer()==2){
          setPlayer(1);
        }
      }
  
      @Override
      int distributeStones(final int startingPoint){
          int playNum=2;
          int stoppingPoint=0;
          Countable next;
          if(getCurrentPlayer()==1){
              playNum=1;
          }
          getDataStructure().setIterator(startingPoint, playNum, false);
          int numStones=getDataStructure().removeStones(startingPoint);
          final int returnTotal=numStones;
          while(numStones>0){
            next=getDataStructure().next();
            next.addStone();
            numStones--;
            stoppingPoint=getDataStructure().getIteratorPos();
          }

          if(playNum==1 && stoppingPoint<6 && getDataStructure().getNumStones(stoppingPoint+1)==1){
            captureStones(stoppingPoint+1);
          }
          else if(playNum==2 && stoppingPoint>6 && stoppingPoint<13 && getDataStructure().getNumStones(stoppingPoint)==1){
            captureStones(stoppingPoint);
          }

          if (stoppingPoint == 6 && playNum == 1) {
            aBonus = true;
          } else if (stoppingPoint == 13 && playNum == 2) {
              aBonus = true;
          } else {
              aBonus = false;
          }

          if(!aBonus){
            switchPlayers();
          }

          return returnTotal;
    }
}
