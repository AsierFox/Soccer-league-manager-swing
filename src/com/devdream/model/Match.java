package com.devdream.model;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class Match {

	@DBKey(key=Key.PRIMARY)
	private int id;
	private Team homeTeam;
	private Team awayTeam;
	
}
