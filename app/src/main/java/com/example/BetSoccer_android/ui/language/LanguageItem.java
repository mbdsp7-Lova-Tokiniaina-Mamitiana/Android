package com.example.BetSoccer_android.ui.language;

public class LanguageItem {
    private String language;
    private int idLogo;
    private boolean selected=false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(int idLogo) {
        this.idLogo = idLogo;
    }

    public LanguageItem(String language, int idLogo) {
        this.language = language;
        this.idLogo = idLogo;
    }
    public boolean equals(LanguageItem item){
        return this.getLanguage().equals(item.getLanguage());
    }

}
