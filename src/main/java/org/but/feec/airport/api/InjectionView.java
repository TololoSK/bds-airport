package org.but.feec.airport.api;

public class InjectionView {
	private String text;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
    public String toString() {
        return "PersonAuthView{" +
                "text='" + text + '\'' +
                '}';
    }
}


