package com.donglu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model{
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private String text5;
	private String text6;
	private String text7;
	private String text8;
	private String text9;
	private String text10;
	private String text11;
	private String text12;
	private String text13;
	private String text14;
	private String text15;
	private String text16;
	private String text17;
	private String text18;
	private String text19;
	private String text20;
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
		pcs.firePropertyChange("text1", null, null);
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
		pcs.firePropertyChange("text2", null, null);
	}
	public String getText3() {
		return text3;
	}
	public void setText3(String text3) {
		this.text3 = text3;
		pcs.firePropertyChange("text3", null, null);
	}
	public String getText4() {
		return text4;
	}
	public void setText4(String text4) {
		this.text4 = text4;
		pcs.firePropertyChange("text4", null, null);
	}
	public String getText5() {
		return text5;
	}
	public void setText5(String text5) {
		this.text5 = text5;
		pcs.firePropertyChange("text5", null, null);
	}
	public String getText6() {
		return text6;
	}
	public void setText6(String text6) {
		this.text6 = text6;
		pcs.firePropertyChange("text6", null, null);
	}
	public String getText7() {
		return text7;
	}
	public void setText7(String text7) {
		this.text7 = text7;
		pcs.firePropertyChange("text7", null, null);
	}
	public String getText8() {
		return text8;
	}
	public void setText8(String text8) {
		this.text8 = text8;
		pcs.firePropertyChange("text8", null, null);
	}
	public String getText9() {
		return text9;
	}
	public void setText9(String text9) {
		this.text9 = text9;
		pcs.firePropertyChange("text9", null, null);
	}
	public String getText10() {
		return text10;
	}
	public void setText10(String text10) {
		this.text10 = text10;
		pcs.firePropertyChange("text10", null, null);
	}
	public String getText11() {
		return text11;
	}
	public void setText11(String text11) {
		this.text11 = text11;
		pcs.firePropertyChange("text11", null, null);
	}
	public String getText12() {
		return text12;
	}
	public void setText12(String text12) {
		this.text12 = text12;
		pcs.firePropertyChange("text12", null, null);
	}
	public String getText13() {
		return text13;
	}
	public void setText13(String text13) {
		this.text13 = text13;
		pcs.firePropertyChange("text13", null, null);
	}
	public String getText14() {
		return text14;
	}
	public void setText14(String text14) {
		this.text14 = text14;
		pcs.firePropertyChange("text14", null, null);
	}
	public String getText15() {
		return text15;
	}
	public void setText15(String text15) {
		this.text15 = text15;
		pcs.firePropertyChange("text15", null, null);
	}
	public String getText16() {
		return text16;
	}
	public void setText16(String text16) {
		this.text16 = text16;
		pcs.firePropertyChange("text16", null, null);
	}
	public String getText17() {
		return text17;
	}
	public void setText17(String text17) {
		this.text17 = text17;
		pcs.firePropertyChange("text17", null, null);
	}
	public String getText18() {
		return text18;
	}
	public void setText18(String text18) {
		this.text18 = text18;
		pcs.firePropertyChange("text18", null, null);
	}
	public String getText19() {
		return text19;
	}
	public void setText19(String text19) {
		this.text19 = text19;
		pcs.firePropertyChange("text19", null, null);
	}
	public String getText20() {
		return text20;
	}
	public void setText20(String text20) {
		this.text20 = text20;
		pcs.firePropertyChange("text20", null, null);
	}
	
}
