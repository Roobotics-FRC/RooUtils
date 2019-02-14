public class EncoderOutputCommand extends Command {

    private Drivetrain drivetrain;

    private HashMap<Long, Integer> logs;
    private boolean logged = false;

    public JoystickControl() {
        this.logs = new HashMap();
        requires(this.drivetrain = Drivetrain.getInstance());
    }


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = OI.getOI().getDriveJoystick().rooGetZ();

        double curRight = drivetrain.getRight();
        double curLeft = drivetrain.getLeft();

        this.drivetrain.setBoth(y);
        if (OI.getOI().getDriveJoystick().getRawButton(7)) {
            this.drivetrain.setRight(y + z);
            this.drivetrain.setLeft(y - z);
        }

        this.logs.put(System.currentTimeMillis(), this.drivetrain.getRightPosition());

        // Write out on press of button 10
        if (OI.getOI().getDriveJoystick().getRawButton(10) && !logged) {
            System.out.println("********************************");
            logged = true;
            StringBuilder sb = new StringBuilder();
            logs.forEach((Long time, Integer log) -> {
                sb.append(time.toString());
                sb.append(",");
                sb.append(log.toString());
                sb.append("\n");
            });
            System.out.println(sb.toString());
            System.out.println("********************************");
        }
    }

    @Override
    public void interrupted() {
        this.end();
    }

    @Override
    public void end() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}