package controller;

public enum ActionEnum {
	SHOW_CREATE_DIALOG, CHANGE_IMAGE_WEB, CHANGE_IMAGE_LOCAL_SERVER, CREATE, CANCEL_CREATION, CHANGE_MODE, ADD_TO_CAR, REMOVE_OF_THE_CAR, OPEN_CAR, BUY, DELETE_PRODUCT, EDIT_PRODUCT, SEARCH, VIEW_PRODUCT, PREV_PAGE, NEXT_PAGE, FILTER, LOGIN, BUTTON_CHANGE_BETWEEN_USER_AND_ADMIN, REGISTER, NEXT_PURCHASE, PREV_PURCHASE, VIEW_PURCHASE,SHOW_STATISTICS;
	@Override
	public String toString() {
		String name = name().replaceAll("_", " ").toLowerCase();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
}