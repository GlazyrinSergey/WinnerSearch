
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import org.example.*;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Тест для первой версии программы. Предназначен для проверки корректной чтения файлаи заполнения массива.
 *
 * В данный момент не рабочий.
 */
public class ButtonPanelTest {
    Path path = Path.of("C:\\Users\\Михаил\\IdeaProjects\\CrptApi\\WinnerSearchGit\\WinnerSearch\\src\\static\\участникиТест.txt");
    List<String> strTest = List.of("Дмитрий", "Sergey Krivchikov", "Rodion Akhmetov");
    @Test
    void getArrayOfParTest(){
        WinnerSearch.ButtonPanel bp = new ButtonPanel();
        var bcf = bp.new ButtonChooseFile();
        List<String> str = Arrays.asList(bcf.getArrayOfPar(path));
        assertLinesMatch(strTest,str);
    }
}
