package com.example.brainboggle3;

public class ListItem_sol
{
    private  String Qu,sola,solb,solc,sold,solcorr,sol_score;

    public ListItem_sol(String Qu, String sola, String solb,String solc ,String sold,String solcorr,String sol_score)
    {
        this.Qu=Qu;
        this.sola=sola;
        this.solb=solb;
        this.solc=solc;            //each question options given,correct option and selected option   if slected option is not correct option set selected option as red and correct option as green else correctedoption is grren
        this.sold=sold;
        this.solcorr=solcorr;
        this.sol_score=sol_score;
        //this.sel=sel;

    }
    public String getQuestion()
    {
        return Qu;
    }
    public String getSola()
    {
        return sola;
    }
    public String getSolb()
    {
        return solb;
    }
    public String getSolc()
    {
        return solc;
    }
    public String getSold()
    {
        return sold;
    }
    public String getSolcorr()
    {
        return solcorr;
    }
    public String getSol_score(){return sol_score;}
   /* public  String getSel(){return  sel;}*/

}
