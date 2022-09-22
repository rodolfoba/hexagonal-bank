package framework.usecase;

public interface UseCase<I extends UseCase.InputData, P extends UseCase.Presenter> {

    void execute(I inputData, P presenter);

    interface InputData {}
    interface Presenter {}

}
