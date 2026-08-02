

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginUI extends JFrame {

    public LoginUI() {
        initUI();
    }

    // 初始化登录界面
    private void initUI() {
        setTitle("用户登录");
        setSize(430, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GradientPanel bg = new GradientPanel();
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // 中间白色圆角登录卡片
        RoundedPanel card = new RoundedPanel();
        card.setPreferredSize(new Dimension(350, 450));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 35));
        buildCardContent(card);

        bg.add(card);
    }

    // 构建登录卡片内的所有组件
    private void buildCardContent(RoundedPanel card) {
        JLabel title = new JLabel("欢迎登录");
        title.setFont(new Font("微软雅黑", Font.BOLD, 26));
        title.setForeground(new Color(0x2D3436));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("请输入您的账号和密码");
        subTitle.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        subTitle.setForeground(new Color(0x95A5A6));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        PlaceholderTextField usernameField = new PlaceholderTextField("请输入用户名");
        PlaceholderPasswordField passwordField = new PlaceholderPasswordField("请输入密码");

        RoundedButton loginButton = new RoundedButton("登 录", new Color(0x667EEA), new Color(0x5A67D8), Color.WHITE);
        RoundedButton resetButton = new RoundedButton("重 置", new Color(0xE2E8F0), new Color(0xD3D9E3), new Color(0x64748B));

        loginButton.addActionListener(e -> handleLogin(usernameField, passwordField));
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        });

        JLabel registerTip = new JLabel("<html><font color='#95A5A6'>还没有账号？</font><font color='#667EEA'><u>立即注册</u></font></html>");
        registerTip.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        registerTip.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerTip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subTitle);
        card.add(Box.createVerticalStrut(30));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(16));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(28));
        card.add(loginButton);
        card.add(Box.createVerticalStrut(14));
        card.add(resetButton);
        card.add(Box.createVerticalStrut(20));
        card.add(registerTip);
    }

    // 处理登录逻辑
    private void handleLogin(PlaceholderTextField usernameField, PlaceholderPasswordField passwordField) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "登录成功，欢迎您：" + username, "登录成功", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}

// 渐变背景面板
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // 绘制蓝紫色渐变背景
        GradientPaint gp = new GradientPaint(0, 0, new Color(0x667EEA), getWidth(), getHeight(), new Color(0x764BA2));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}

// 半透明圆角卡片面板
class RoundedPanel extends JPanel {
    public RoundedPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 240));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
        super.paintComponent(g2);
        g2.dispose();
    }
}

// 带占位提示文字的圆角输入框
class PlaceholderTextField extends JTextField {
    private final String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        setFont(new Font("微软雅黑", Font.PLAIN, 14));
        setForeground(new Color(0x2D3436));
        setCaretColor(new Color(0x667EEA));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(280, 44));
        setMaximumSize(new Dimension(280, 44));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0xF2F3F7));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
        super.paintComponent(g2);
        if (getText().isEmpty()) {
            g2.setColor(new Color(0xA0A8B0));
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(placeholder, 20, getHeight() / 2 + (fm.getAscent() - fm.getDescent()) / 2);
        }
        g2.dispose();
    }
}

// 带占位提示文字的圆角密码框
class PlaceholderPasswordField extends JPasswordField {
    private final String placeholder;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        setFont(new Font("微软雅黑", Font.PLAIN, 14));
        setForeground(new Color(0x2D3436));
        setCaretColor(new Color(0x667EEA));
        setEchoChar('●');
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(280, 44));
        setMaximumSize(new Dimension(280, 44));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0xF2F3F7));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
        super.paintComponent(g2);
        if (getPassword().length == 0) {
            g2.setColor(new Color(0xA0A8B0));
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(placeholder, 20, getHeight() / 2 + (fm.getAscent() - fm.getDescent()) / 2);
        }
        g2.dispose();
    }
}

// 圆角渐变按钮（带悬停变色效果）
class RoundedButton extends JButton {
    private final Color normalColor;
    private final Color hoverColor;
    private boolean hovering;

    public RoundedButton(String text, Color normalColor, Color hoverColor, Color textColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        setForeground(textColor);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFont(new Font("微软雅黑", Font.BOLD, 15));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(280, 46));
        setMaximumSize(new Dimension(280, 46));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 鼠标悬停时渐变方向反向，实现变色效果
        GradientPaint gp = new GradientPaint(0, 0, hovering ? hoverColor : normalColor,
                getWidth(), getHeight(), hovering ? normalColor : hoverColor);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 23, 23);
        super.paintComponent(g2);
        g2.dispose();
    }
}
