package com.devdream.validator;

import java.sql.SQLException;

import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.model.Performance;
import com.devdream.model.SeasonGame;
import com.devdream.util.DateHelper;
import com.devdream.util.MathHelper;

/**
 * Validates the performance statistic of a season game.
 * 
 * @author Asier Gonzalez
 */
public class PerformanceValidator {

	private String gameDate, leagueStartDate, leagueEndDate;
	
	public PerformanceValidator() {}
	
	public PerformanceValidator(String gameDate) throws InvalidInputException, SQLException {
		this.gameDate = gameDate;
		LeagueVO leagueVO = new LeagueDAO().getCurrentLeague();
		leagueStartDate = leagueVO.getStartDate();
		leagueEndDate = leagueVO.getEndDate();
	}
	
	public void validate(Performance performance) throws InvalidInputException {
		if (gameDate.equals(SeasonGame.EMPTY_DATE)) {
			throw new InvalidInputException("You need to set a date!");
		}
		if (!DateHelper.isDateWithinPeriod(gameDate, leagueStartDate, leagueEndDate)) {
			throw new InvalidInputException("The date must be within the league period!");
		}
		if (MathHelper.isNegativeNumber(performance.getShots()) || MathHelper.isNegativeNumber(performance.getPasses()) ||
				MathHelper.isNegativeNumber(performance.getFouls()) || MathHelper.isNegativeNumber(performance.getOffsides()) ||
				MathHelper.isNegativeNumber(performance.getCorners()) || MathHelper.isNegativeNumber(performance.getScore()))
		{
			throw new InvalidInputException("The integers can't be negative");
		}
	}

}
