package paintApp;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class PaintApp {
    static int w = 800, h = 600;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // JFrameのインスタンスを生成
        JFrame frame = new JFrame("お絵かきアプリ");
        // ウィンドウを閉じたらプログラムを終了する
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ウィンドウのサイズ・初期位置
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        // setBounds(x, y, w, h);

        // PaintCanvasのインスタンスを生成
        PaintCanvas canvas = new PaintCanvas();

        // フレームに追加
        JPanel pane = new JPanel();
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        JPanel paneB = new JPanel();
        frame.getContentPane().add(paneB, BorderLayout.NORTH);

        canvas.setPreferredSize(new Dimension(w, h));
        pane.add(canvas);

        /* 追加機能 */
        // 全消去
        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ClearListener(canvas));
        paneB.add(clear);

        // 線の太さ調節
        JSlider slider = new JSlider(1, 50, 1); // 最小値、最大値、初期値
        slider.addChangeListener(new SliderListener(canvas)); // 割り込み処理用
        paneB.add(slider);

        // 線の色変更
        String[] combodata = { "BLACK", "CYAN", "BLUE", "GREEN" };
        JComboBox combo = new JComboBox(combodata);
        combo.addActionListener(new ComboListener(canvas));
        paneB.add(combo);

        // ウィンドウを表示
        frame.setVisible(true);
    }

    // キャンバスクラス
    static class PaintCanvas extends Canvas implements MouseListener,
            MouseMotionListener {

        // 描画内容を保持するBufferedImage
        private BufferedImage cImage = null;
        // cImageに描画するためのインスタンス
        private Graphics2D g2d;

        // 線の開始座標・終了座標
        private int x, y, xx, yy;
        // 描画モードＯＲ消しゴムモード
        private int type;
        // 線の太さ
        public int width = 1;
        // 線の色
        public Color c = Color.black;

        public PaintCanvas() {
            // 座標を初期化
            x = -1;
            y = -1;
            xx = -1;
            yy = -1;
            type = 0;

            // MouseListener・MouseMotionListenerを設定
            addMouseListener(this);
            addMouseMotionListener(this);

            // キャンバスの背景を白に設定
            setBackground(Color.white);
            // 描画内容を保持するBufferedImageを生成
            cImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            g2d = (Graphics2D) cImage.getGraphics();
            // BufferedImageの背景も白にする
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, w, h);

            // 描画
            repaint();
        }

        // キャンバスをクリア
        public void clear() {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, w, h);
            repaint();
        }

        // 線の太さ変更
        public void setStroke(int n) {
            width = n;
        }

        // 線の色変更
        public void setColorCombo(String color) {
            if (color.equals("BLACK")) {
                c = Color.black;
            } else if (color.equals("CYAN")) {
                c = Color.cyan;
            } else if (color.equals("BLUE")) {
                c = Color.blue;
            } else if (color.equals("GREEN")) {
                c = Color.green;
            }
        }

        public void paint(Graphics g) {
            // 描画モード（線分を描画）
            if (type == 1) {
                if (x >= 0 && y >= 0 && xx >= 0 && yy >= 0) {
                    BasicStroke stroke = new BasicStroke(width,
                            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                    g2d.setStroke(stroke);
                    g2d.setColor(c);
                    g2d.drawLine(xx, yy, x, y);
                }
                // 消しゴムモード
            } else if (type == 2) {
                if (x >= 0 && y >= 0 && xx >= 0 && yy >= 0) {
                    // 両端が丸い線分に設定
                    BasicStroke stroke = new BasicStroke(50.0f,
                            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                    g2d.setStroke(stroke);
                    g2d.setColor(Color.white);
                    g2d.drawLine(xx, yy, x, y);
                }
            }

            // 描画内容をキャンバスにも反映
            g.drawImage(cImage, 0, 0, null);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // 押されているボタンを検知
            if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
                // 左ボタンクリック　（描画モード）
                type = 1;
            }
            if ((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) {
                // 中央ボタンクリック
            }
            if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                // 右ボタンクリック　（消しゴムモード）
                type = 2;
            }

            // 過去の座標を開始座標に設定
            xx = x;
            yy = y;

            // 新しい座標を終了座標に設定
            Point point = e.getPoint();
            x = point.x;
            y = point.y;

            // 再描画
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // ドラッグが終了したら座標を初期化
            x = -1;
            y = -1;
            xx = -1;
            yy = -1;
            type = 0;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Point point = e.getPoint();
            x = point.x;
            y = point.y;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    // クリアボタン用
    static class ClearListener implements ActionListener {

        PaintCanvas canvas;

        public ClearListener(PaintCanvas canvas) {
            super();
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.clear();
        }

    }

    // スライダー用
    static class SliderListener implements ChangeListener {

        PaintCanvas canvas;

        public SliderListener(PaintCanvas canvas) {
            super();
            this.canvas = canvas;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            int fps = (int) source.getValue();
            canvas.setStroke(fps);
        }

    }

    // コンボボックス用
    static class ComboListener implements ActionListener {

        PaintCanvas canvas;

        public ComboListener(PaintCanvas canvas) {
            super();
            this.canvas = canvas;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            String color = (String) source.getSelectedItem();
            canvas.setColorCombo(color);
        }

    }

}