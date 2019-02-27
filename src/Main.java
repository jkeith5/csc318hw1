import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        NewsBoy newsBoy= new NewsBoy(5.6,16,0);
        NewsBoy newsBoy1=new NewsBoy(5.6,15,1);
        NewsBoy newsBoy2=new NewsBoy(5.6,15,2);
        int i=0;
        System.out.println();
        for (i=0; i<1000;i++) {//this is for part a
            int demandDaily = papersDemanded();
            newsBoy.papersPurchased(i);
            newsBoy.demandStats.add(demandDaily);//adds demand to stats arraylist
            newsBoy.sellPapers(demandDaily);
            newsBoy1.papersPurchased(i);
            newsBoy1.demandStats.add(demandDaily);//adds demand to stats arraylist
            newsBoy1.sellPapers(demandDaily);
            newsBoy2.papersPurchased(i);
            newsBoy2.demandStats.add(demandDaily);//adds demand to stats arraylist
            newsBoy2.sellPapers(demandDaily);

        }
        System.out.printf("\t\t\t\tFinal Stats\n\t\tOrder Policy #1 (16 papers/day) \n\tAverage Sold:%2.4f \tAverage Demand:%2.4f\tAverage Profit:%2.4f\n" +
                "\tSales Variance: %f\t Demand Variance: %f \t Profit Variance: %f\n",
                newsBoy.calcAverageSold(),newsBoy.calcAverageDemand(),newsBoy.calcAverageProfit(),
                newsBoy.calcVariance(newsBoy.calcAverageSold(), newsBoy.papersSoldStats), newsBoy.calcVariance(newsBoy.calcAverageDemand(), newsBoy.demandStats),
                newsBoy.calcVariancee(newsBoy.calcAverageProfit(), newsBoy.profitStats));
        System.out.printf("\t\tOrder Policy #2 (Prev. Day's Demand) \n\tAverage Sold:%2.4f \tAverage Demand:%2.4f\tAverage Profit:%2.4f\n" +
                        "\tSales Variance: %f\t Demand Variance: %f \t Profit Variance: %f\n",
                newsBoy1.calcAverageSold(),newsBoy1.calcAverageDemand(),newsBoy1.calcAverageProfit(),
                newsBoy1.calcVariance(newsBoy1.calcAverageSold(), newsBoy1.papersSoldStats), newsBoy1.calcVariance(newsBoy1.calcAverageDemand(), newsBoy1.demandStats),
                newsBoy1.calcVariancee(newsBoy1.calcAverageProfit(), newsBoy1.profitStats));
        System.out.printf("\t\tOrder Policy #3 (Prev. Day's Demand -1) \n\tAverage Sold:%2.4f \tAverage Demand:%2.4f\tAverage Profit:%2.4f\n" +
                        "\tSales Variance: %f\t Demand Variance: %f \t Profit Variance: %f\n",
                newsBoy2.calcAverageSold(),newsBoy2.calcAverageDemand(),newsBoy2.calcAverageProfit(),
                newsBoy2.calcVariance(newsBoy2.calcAverageSold(), newsBoy2.papersSoldStats), newsBoy2.calcVariance(newsBoy2.calcAverageDemand(), newsBoy2.demandStats),
                newsBoy2.calcVariancee(newsBoy2.calcAverageProfit(), newsBoy2.profitStats));
    }



    public static int papersDemanded(){
        int demand=0;
        double randomNum=(Math.random()); //1/12 ~~ .0833
        if (randomNum<.0833){//demand is 15
            demand=15;
        }else if (randomNum<.1666){// demand is 18
            demand=16;
        } else if(randomNum<.5833){//demand is 17
            demand=17;
        }else if(randomNum<.75){
            demand=18;
        }else if(randomNum<.916){
            demand=19;
        }else {
            demand=20;
        }

        return demand;
    }

}
class NewsBoy{
    ArrayList<Integer> papersSoldStats= new ArrayList<>();
    ArrayList<Integer> demandStats= new ArrayList<>();
    ArrayList<Double> profitStats= new ArrayList<>();
    int papersOnHand;
    int papersSold;
    double money;
    int papersWanted;
    int demand;
    int type;
    double profit;



    public NewsBoy(double money, int papersWanted,int type) {// type 0 is 16papers/day, 1 is the previous demand, 2 is previous -1
        this.money = money;
        this.papersWanted = papersWanted;
        this.type=type;
        this.demand=demand;
        this.profit=0;

    }
    public void determinePapersOrdered(int days){
        if (this.type==0){
            this.papersWanted=16;
        }else if(this.type==1){
            if (demandStats.size()!=0){
                this.papersWanted=this.demandStats.get(days-1);
            }else{
                this.papersWanted=15;
            }
        }else if(type==2){
            if (demandStats.size()!=0){
                this.papersWanted=this.demandStats.get(days-1)-1;
            }else{
             this.papersWanted=15;
            }
        }
    }
    public void papersPurchased(int days){
        determinePapersOrdered(days);
        int purchased=0;
        if (.35*papersWanted<=this.money){//checks if he has enough money to purchase papers
            this.money-=.35*papersWanted;
            purchased=papersWanted;
        } else{//he doesnt
            while(this.money!=0){
                if (this.money-.35>0){
                    this.money-=.35;
                    purchased++;
                }else{
                    break;
                }
            }
        }
        this.papersOnHand=purchased;
    }
    public void sellPapers(int demand){//this fucntion handles the sale and return of papers
        if (papersOnHand>=demand) {//if more or equal amount of papers are needed than demanded
            this.papersSold = demand;
            this.money+=papersSold;
            this.papersOnHand-=demand;
            this.profit=(papersSold*.65);
            if (papersOnHand>0){//return extra papers
                this.money+=(papersOnHand*.05);
                papersOnHand=0;
            }
            this.profitStats.add(profit);
            this.papersSoldStats.add(papersSold);
            this.papersSold=0;
        }else {//if less papers are needed than demanded
            this.papersSold=papersOnHand;
            this.money+=papersSold;
            this.papersOnHand=0;
            this.profit=(papersSold*.65);
            this.profitStats.add(profit);
            this.papersSoldStats.add(papersSold);
            this.papersSold=0;
        }
    }
    public double calcAverageSold(){
        double averageSold=0;
        int totalSold=0;
        for(int i=0; i<1000;i++){
            totalSold+=papersSoldStats.get(i);
        }
        averageSold= (double)totalSold / 1000;
        return averageSold;
    }
    public double calcAverageDemand(){
        double averageDemand=0;
        int totalDemand=0;
        for(int i=0; i<1000;i++){
            totalDemand+=demandStats.get(i);
        }
        averageDemand= (double)totalDemand / demandStats.size();
        return averageDemand;
    }
    public double calcAverageProfit(){
        double averageProfit=0;
        int totalProfit=0;
        for(int i=0; i<1000;i++){
            totalProfit+=profitStats.get(i);
        }
        averageProfit= (double)totalProfit / profitStats.size();
        return averageProfit;
    }

    public double calcVariance(double average, ArrayList<Integer> list ){//calculates the variance
        double variance=0;
        double temp=0;
        for (int i =0; i<1000; i++){// summation of x sub i minus x bar squared
            temp+= Math.pow(list.get(i)-average,2);
        }
        variance= temp/list.size(); //divides by N
        return variance;
    }
    public double calcVariancee(double average, ArrayList<Double> list ){//calculates the variance
        double variance=0;
        double temp=0;
        for (int i =0; i<1000; i++){// summation of x sub i minus x bar squared
            temp+= Math.pow(list.get(i)-average,2);
        }
        variance= temp/list.size(); //divides by N
        return variance;
    }
    @Override
    public String toString() {
        return " ";
    }

    public ArrayList<Integer> getDemandStats() {
        return demandStats;
    }

    public void setDemandStats(ArrayList<Integer> demandStats) {
        this.demandStats = demandStats;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList getPapersSoldStats() {
        return papersSoldStats;
    }

    public void setPapersSoldStats(ArrayList papersSoldStats) {
        this.papersSoldStats = papersSoldStats;
    }

    public int getPapersOnHand() {
        return papersOnHand;
    }

    public void setPapersOnHand(int papersOnHand) {
        this.papersOnHand = papersOnHand;
    }

    public int getPapersSold() {
        return papersSold;
    }

    public void setPapersSold(int papersSold) {
        this.papersSold = papersSold;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPapersWanted() {
        return papersWanted;
    }

    public void setPapersWanted(int papersWanted) {
        this.papersWanted = papersWanted;
    }
}
