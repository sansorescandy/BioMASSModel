package multiagent.model.agent;

/**
 * @author candysansores
 * 
 */
public class ReactiveAgent extends Agent {

   private Behavior[] behaviors;
   private boolean suppresses[][];
   private int currentBehaviorIndex;
   

   protected void initBehaviors(Behavior[] behaviors,
         boolean subsumptionRelationships[][]) {
      this.behaviors = behaviors;
      this.suppresses = subsumptionRelationships;
   }


   protected void initBehavior() {
   	// TODO Auto-generated method stub
	   currentBehaviorIndex = 0;
   }
  
   // @see multiagent.model.agent.Agent#performBehavior()
   public void performBehavior() {
      boolean isActive[] = new boolean[behaviors.length];
      for (int i = 0; i < isActive.length; i++) {
         isActive[i] = behaviors[i].isActive();
      }
      boolean ranABehavior = false;
      while (!ranABehavior) {
         boolean runCurrentBehavior = isActive[currentBehaviorIndex];
         if (runCurrentBehavior) {
            for (int i = 0; i < suppresses.length; i++) {
               if (isActive[i] && suppresses[i][currentBehaviorIndex]) {
                  runCurrentBehavior = false;
                  break;
               }
            }
         }

         if (runCurrentBehavior) {
            if (currentBehaviorIndex < behaviors.length) {
                 behaviors[currentBehaviorIndex].act();
            }
            ranABehavior = true;
         }

         if (behaviors.length > 0) {
            currentBehaviorIndex = (currentBehaviorIndex + 1)
                  % behaviors.length;
         }
      }
   }


}
