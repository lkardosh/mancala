package mancala;

public class AyoRules extends GameRules{

    public AyoRules() {
      super();
  }
     
    @Override
    int captureStones(final int stoppingPoint){
        final int numCaptured=getDataStructure().removeStones(13-stoppingPoint);
        if (numCaptured==0){
          return 0;
        }
        else{
          getDataStructure().removeStones(stoppingPoint);
          if(stoppingPoint <=6){
              getDataStructure().addToStore(1, numCaptured+1);
          }else if(stoppingPoint>6 && stoppingPoint<13){
              getDataStructure().addToStore(2, numCaptured+1);
        }
          return numCaptured;
        }
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

     private void switchPlayers(){
      if(getCurrentPlayer()==1){
        setPlayer(2);
      }
      else{
        setPlayer(1);
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
        switchPlayers();
        return newNumStones-originalNumStones;      
     }

      @Override
      int distributeStones(final int startingPoint){
        int playNum=getCurrentPlayer();
        int stoppingPoint=1;
        Countable next=null;
        getDataStructure().setIterator(startingPoint, playNum, true);
        int numStones=getDataStructure().removeStones(startingPoint);
        final int returnTotal=numStones;
        //ahhhhh
        while(numStones>0){
          next=getDataStructure().next();
          next.addStone();
          numStones--;
          if(numStones==0){
            stoppingPoint=getDataStructure().getIteratorPos();
              if(stoppingPoint>=0 && stoppingPoint<6){
                if(getDataStructure().getNumStones(stoppingPoint+1)>1){
                  // System.out.println("stoppingPoint: " + stoppingPoint);
                  numStones=getDataStructure().removeStones(getDataStructure().getIteratorPos()+1);
                }
              }
              else if(stoppingPoint>=7 && stoppingPoint<13){
                if(getDataStructure().getNumStones(stoppingPoint)>1){
                  numStones=getDataStructure().removeStones(getDataStructure().getIteratorPos());
                }
              }
          }        
          // System.out.println("numStones: " + numStones);
          //stoppingPoint=getDataStructure().getIteratorPos();
        }
        if(playNum==1 && stoppingPoint<6 && getDataStructure().getNumStones(stoppingPoint+1)==1){
          captureStones(stoppingPoint+1);
        }
        else if(playNum==2 && stoppingPoint>6 && stoppingPoint<13 && getDataStructure().getNumStones(stoppingPoint)==1){
          captureStones(stoppingPoint);
        }
        return returnTotal;
  }
}