package me.marc3308.klassensysteem.objekte;

import java.util.List;

public class party {

    private String Owner;
    private List<String> Mitglieder;
    public party(String owner, List<String> Mitglieder){

        this.Mitglieder=Mitglieder;
        this.Owner=owner;
    }

    public String getOwner() {
        return Owner;
    }

    public List<String> getMitglieder() {
        return Mitglieder;
    }

    public void setMitglieder(List<String> mitglieder) {
        Mitglieder = mitglieder;
    }

    public void removeMitglied(String mitglied){
        List<String> Mitgliederlist=getMitglieder();
        Mitgliederlist.remove(mitglied);
        setMitglieder(Mitgliederlist);
    }

    public void addMitlgied(String mitglied){
        List<String> Mitgliederlist=getMitglieder();
        Mitgliederlist.add(mitglied);
        setMitglieder(Mitgliederlist);
    }
}
