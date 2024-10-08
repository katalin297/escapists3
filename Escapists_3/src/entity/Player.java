 package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
   GamePanel gp;
   KeyHandler keyH;
   public final int screenX;
   public final int screenY;
   public int hasKey = 0;
   boolean bootsOn = false;
   int bootsCounter = 0;

   public Player(GamePanel gp, KeyHandler keyH) {
      this.gp = gp;
      this.keyH = keyH;
      gp.getClass();
      int var10001 = 768 / 2;
      gp.getClass();
      this.screenX = var10001 - 48 / 2;
      gp.getClass();
      var10001 = 576 / 2;
      gp.getClass();
      this.screenY = var10001 - 48 / 2;
      
      this.setDefaultValues();
      this.getPlayerImage();
   }

   public void setDefaultValues() {
      this.gp.getClass();
      this.worldX = 48 * 23;
      this.gp.getClass();
      this.worldY = 48 * 21;
      this.speed = 4;
      this.direction = "down";
   }

   public void getPlayerImage() {
      try {
         this.up1 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_up_1.png"));
         this.up2 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_up_2.png"));
         this.down1 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_down_1.png"));
         this.down2 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_down_2.png"));
         this.left1 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_left_1.png"));
         this.left2 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_left_2.png"));
         this.right1 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_right_1.png"));
         this.right2 = ImageIO.read(this.getClass().getResourceAsStream("/player/boy_right_2.png"));
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public void update() {
      if (this.keyH.upPressed || this.keyH.downPressed || this.keyH.leftPressed || this.keyH.rightPressed) {
         if (this.keyH.upPressed) {
            this.direction = "up";
         } else if (this.keyH.downPressed) {
            this.direction = "down";
         } else if (this.keyH.leftPressed) {
            this.direction = "left";
         } else if (this.keyH.rightPressed) {
            this.direction = "right";
         }

         

         ++this.spriteCounter;
         if (this.spriteCounter > 12) {
            if (this.spriteNum == 1) {
               this.spriteNum = 2;
            } else if (this.spriteNum == 2) {
               this.spriteNum = 1;
            }

            this.spriteCounter = 0;
         }
      }

   }

  
   public void draw(Graphics2D g2) {
      BufferedImage image = null;
      String var3;
      switch((var3 = this.direction).hashCode()) {
      case 3739:
         if (var3.equals("up")) {
            if (this.spriteNum == 1) {
               image = this.up1;
            }

            if (this.spriteNum == 2) {
               image = this.up2;
            }
         }
         break;
      case 3089570:
         if (var3.equals("down")) {
            if (this.spriteNum == 1) {
               image = this.down1;
            }

            if (this.spriteNum == 2) {
               image = this.down2;
            }
         }
         break;
      case 3317767:
         if (var3.equals("left")) {
            if (this.spriteNum == 1) {
               image = this.left1;
            }

            if (this.spriteNum == 2) {
               image = this.left2;
            }
         }
         break;
      case 108511772:
         if (var3.equals("right")) {
            if (this.spriteNum == 1) {
               image = this.right1;
            }

            if (this.spriteNum == 2) {
               image = this.right2;
            }
         }
      }

      int var10002 = this.screenX;
      int var10003 = this.screenY;
      this.gp.getClass();
      this.gp.getClass();
      g2.drawImage(image, var10002, var10003, 48, 48, (ImageObserver)null);
   }
}