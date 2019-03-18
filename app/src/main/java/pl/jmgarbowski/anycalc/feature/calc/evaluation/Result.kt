package pl.jmgarbowski.anycalc.feature.calc.evaluation

sealed class Result
class Success(val resultMessage: String) : Result()
class Error(val errorMessage: String) : Result()