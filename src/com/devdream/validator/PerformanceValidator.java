package com.devdream.validator;

import com.devdream.db.vo.LeagueVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.model.Performance;
import com.devdream.util.DateHelper;
import com.devdream.util.MathHelper;

/**
 * Validates the performance statistic of a season game.
 * 
 * @author Asier Gonzalez
 */
public class PerformanceValidator {

	public PerformanceValidator() {}
	
	public PerformanceValidator(String gameDate, LeagueVO leagueVO) throws InvalidInputException {
		if (!DateHelper.isDateWithinPeriod(gameDate, leagueVO.getStartDate(), leagueVO.getEndDate())) {
			throw new InvalidInputException("The date must be within the league period!");
		}
	}
	
	public void validate(Performance performance) throws InvalidInputException {
		if (MathHelper.isNegativeNumber(performance.getShots()) || MathHelper.isNegativeNumber(performance.getPasses()) ||
				MathHelper.isNegativeNumber(performance.getFouls()) || MathHelper.isNegativeNumber(performance.getOffsides()) ||
				MathHelper.isNegativeNumber(performance.getCorners()) || MathHelper.isNegativeNumber(performance.getScore()))
		{
			throw new InvalidInputException("The integers can't be negative");
		}
	}

}
