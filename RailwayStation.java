/**
 * Represents a Railway Station
 * @author Ramy Bachayev
 * @version 30/4/2020 
 */

public class RailwayStation
{   //instance variables:
    private Train [] _station;//represents the RailwayStation by array of trains
    private int _noOfTrs;//represents the number of trains in RailwayStation      

    //defining final variables:
    private final int MAX_TRAINS=100;//maxium trains in RailwayStation
    private final int MINIMUM=0;

    //constructors:
    /**
     * Default constructor RailwayStation.
     * Construct a RailwayStation with maxium trains - 100.           
     */

    public RailwayStation()
    {
        _noOfTrs=MINIMUM;
        _station=new Train [MAX_TRAINS];//defining the array of trains by using class Train    
    }        

    /**
     * Add train to RailwayStation.
     * If train already in the station or there's no place in the station
     * or the inputed train is null, the train won't be added.
     * Added train will add to the first empty location in array station.
     * @param t the train i need to add to the RailwayStation  
     * @return true if the train added to the RailwayStation 
     */

    public boolean addTrain(Train t)
    {  
        if(t!=null)//checks if the inputed train is null
        {
            //avoiding aliasing by using copy constructor in Train class
            Train trainCopy= new Train(t);  

            //check the first empty location in station array to put in the added train
            for(;(_noOfTrs<MAX_TRAINS)&&(_station[_noOfTrs]!=null);_noOfTrs++);

            //if there's another location in station array with the same train, the train will not add 
            for(int j=MINIMUM;j<MAX_TRAINS;j++)    
            {  
                if(_station[j]!=null)//checks if station array in "j" location is not empty                
                    if(t.equals(_station[j]))//using the method equals in Train class            
                        return false;                                        
            }        
            _noOfTrs++;//updates the number of trains in RailwayStation

            if(_noOfTrs>MAX_TRAINS)//if the number of trains more than maxium trains, the train will not add 
            {    
                _noOfTrs=MAX_TRAINS;//updates the number of trains to maxium trains
                return false;
            }

            _station[_noOfTrs-1]= trainCopy;//adding the train to station array in the next empty location               
            return true;
        }
        return false;
    }

    /**
     * Remove train from RailwayStation.
     * If there isn't trains in RailwayStation or the train i want to remove isn't in
     * the RailwayStation or the inputed train is null, the train won't be removed.
     * The removed train will be replaced with the last train in station array,
     * last train in station array will be removed from the last location.
     * @param t the train i need to remove from the RailwayStation  
     * @return true if the train removes from the RailwayStation 
     */

    public boolean removeTrain(Train t)
    {
        if(t!=null)//checks if the inputed train is null
        {
            if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
                return false;

            for(int i=MINIMUM;i<MAX_TRAINS;i++)
            { 
                if(_station[i]!=null)//checks if station array in "i" location is not empty
                {
                    if(t.equals(_station[i]))//using the method equals in Train class
                    { //check if there's another location in station array with the same train 
                        for(int k=0;k<MAX_TRAINS;k++)
                        {
                            if(_station[MAX_TRAINS-1-k]!=null)//checks if station array in last location is not empty
                            {//checks where's the last train located in station array
                                //replacing the cells in station array as the instructions                            
                                _station[i]=_station[MAX_TRAINS-1-k];
                                _station[MAX_TRAINS-1-k]=null;
                                _noOfTrs--;//updates the number of trains in RailwayStation
                                return true;                      
                            }
                        }
                    }
                }
            }                                         
            return false;
        }
        return false;
    }

    /**
     * Return a string representation of the RailwayStation in order (first to last location in array).
     * If there is trains in RailwayStation:
     * "The trains today are:" - 2 options:
     * 1: "Train to "destination" departs at "departure". Train is not full."
     * 2: "Train to "destination" departs at "departure". Train is full."
     * If there isn't trains in RailwayStation:
     * "There are no trains today."
     * @return String representation of the RailwayStation
     */

    public String toString()
    {
        if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
            return "There are no trains today.";

        //defining String s - represents the string of the RailwayStation
        String s="The trains today are:\n";

        //checks if station array in "i" location is not empty  
        for(int i=MINIMUM;(i<MAX_TRAINS)&&(_station[i]!=null);i++)        
            s+=_station[i]+"\n";//automatically using method toString() in Train class     

        return s;
    } 

    /**
     * Return a Time1 representation of first departure to place.
     * If there isn't trains in RailwayStation or the inputed place isn't in
     * RailwayStation or the inputed place is null, the return will be null.
     * @param place the destination of the train in RailwayStation
     * @return Time1 representation of first departure to place 
     */

    public Time1 firstDepartureToDestination (String place)
    {
        if(place!=null)//checks if the inputed place is null
        {
            //defining Time1 variable time - represents the most late time in a day
            Time1 time= new Time1(23,59);//using method Time1(h,m) in Time1 class

            String s="";//defining String s 

            if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
                return null;

            for(int i=MINIMUM;i<MAX_TRAINS;i++)    
            {
                if(_station[i]!=null)//checks if station array in "i" location is not empty
                {//if the inputed place is in RailwayStation + using method getDestination() in Train class
                    if((_station[i].getDestination()).equals(place))
                        s=place;

                    //if the inputed place is in RailwayStation and
                    //departure time of station in "i" location before variable time
                    //using method getDeparture() in Train class + method before in Time1 class
                    if(((_station[i].getDeparture()).before(time))&&
                    (_station[i].getDestination()).equals(place))
                        time=_station[i].getDeparture();                   
                }            
            }
            if((s=="")&&(place!=""))//if the inputed place isn't in RailwayStation
                return null;

            return time;
        }
        return null;
    }

    /**
     * Return how much full trains there is in RailwayStation.
     * @return how much full trains there is in RailwayStation 
     */

    public int howManyFullTrains()
    {
        int count=MINIMUM;//defining variable count - number of full trains 

        for(int i=MINIMUM;i<MAX_TRAINS;i++)    
        {
            if(_station[i]!=null)//checks if station array in "i" location is not empty            
                if(_station[i].isFull())//check if train is full by using method isFull in Train class
                    count++;            
        }
        return count;
    }

    /**
     * Return the most popular destination in RailwayStation.
     * If there isn't trains in RailwayStation the return will be null.
     * If there is more than 1 most popular destination, the return will be the
     * first most popular destination in RailwayStation
     * @return the most popular destination in RailwayStation 
     */

    public String mostPopularDestination()
    {
        if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
            return null;

        String dest="";//defining String dest - the most popular destination        
        int popular=MINIMUM;//defining variable popular - counts the most popular destination

        for(int i=MINIMUM;i<MAX_TRAINS;i++)
        {
            int count=MINIMUM;//defining variable count - counts the trains with the same destinations

            if(_station[i]!=null)//checks if station array in "i" location is not empty            
            {
                for(int j=i+1;j<MAX_TRAINS;j++)
                {
                    if(_station[j]!=null)//checks if station array in "j" location is not empty
                    {//using method getDestination() in Train class                          
                        if((_station[i].getDestination()).equals(_station[j].getDestination()))
                        {//checks how many times the most popular destination appears in RailwayStation                                
                            count++;
                            if(popular<count)
                            {//checks if there is more than one popular destination in RailwayStation
                                popular=count;
                                dest=_station[i].getDestination();
                            }//gets the most popular destination in RailwayStation
                        }                        
                    }
                }                                
            }
        }
        if(popular==MINIMUM)//if there isn't more than one popular destination in RailwayStation
        {
            for(int k=MINIMUM;k<MAX_TRAINS;k++)
            {
                if(_station[k]!=null)//checks if station array in "k" location is not empty
                {//gets the first popular destination in RailwayStation
                    dest=_station[k].getDestination();
                    break;
                }
            }
        }
        return dest;
    }

    /**
     * Return the train with the most expensive ticket in RailwayStation.
     * If there isn't trains in RailwayStation the return will be null.
     * If there is more than 1 train with the most expensive ticket, the return
     * will be the first train with the most expensive ticket in RailwayStation.
     * @return the train with the most expensive ticket in RailwayStation 
     */

    public Train mostExpensiveTicket()
    {
        if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
            return null;
        //defining Train variable expensive - represents a train with cheapest ticket    
        Train expensive=new Train("",MINIMUM,MINIMUM,MINIMUM,MINIMUM,MINIMUM,MINIMUM);//using method Train in Train class

        for(int i=MINIMUM;i<MAX_TRAINS;i++)
        {            
            if(_station[i]!=null)//checks if station array in "i" location is not empty            
            {//checks which train in RailwayStation has the expensive ticket                         
                if(expensive.getPrice()<_station[i].getPrice())//using getPrice() method in Train class                            
                    expensive=_station[i];                                            
            }//gets the most expensive ticket in RailwayStation
        }
        if(expensive.getPrice()==MINIMUM)//if there isn't any train with expensive ticket in RailwayStation than 0
        {
            for(int j=MINIMUM;j<MAX_TRAINS;j++)
            {
                if(_station[j]!=null)//checks if station array in "j" location is not empty
                {//gets the first train with the most expensive ticket in RailwayStation
                    expensive=_station[j];
                    break;
                }
            }
        }        
        return expensive;
    }

    /**
     * Return the train with the longest duration in RailwayStation.
     * If there isn't trains in RailwayStation the return will be null.
     * If there is more than 1 train with the longest duration, the return
     * will be the first train with the longest duration in RailwayStation.
     * @return the train with the longest duration in RailwayStation 
     */

    public Train longestTrain()
    {
        if(_noOfTrs==MINIMUM)//if there isn't trains in RailwayStation
            return null;
        //defining Train variable longest - represents a train with minimum duration    
        Train longest=new Train("",MINIMUM,MINIMUM,MINIMUM,MINIMUM,MINIMUM,MINIMUM);//using method Train in Train class

        for(int i=MINIMUM;i<MAX_TRAINS;i++)
        {            
            if(_station[i]!=null)//checks if station array in "i" location is not empty            
            { //checks which train in RailwayStation has the longest duration                     
                if(longest.getDuration()<_station[i].getDuration())//using getDuration() method in Train class                            
                    longest=_station[i];                                           
            }//gets the longest duration in RailwayStation
        }
        if(longest.getDuration()==MINIMUM)//if there isn't any train with longest duration in RailwayStation than 0
        {
            for(int j=MINIMUM;j<MAX_TRAINS;j++)
            {
                if(_station[j]!=null)//checks if station array in "j" location is not empty
                {//gets the first train with the longest duration in RailwayStation
                    longest=_station[j];
                    break;
                }
            }
        }
        return longest;    
    }
}