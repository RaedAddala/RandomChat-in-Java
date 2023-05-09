import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OnDisplayListener extends WindowAdapter {
    private final ITexter _texter;
    @Override
    public void windowOpened(WindowEvent e) {
        try {
            _texter.QueueForChat();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void windowClosing(WindowEvent e) {
        try {
            _texter.QuitApp();
        } catch (Exception ex) {

        }
    }

    public OnDisplayListener(ITexter texter){
        super();
        _texter = texter;
    }
}
