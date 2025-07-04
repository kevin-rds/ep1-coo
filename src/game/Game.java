package game;

import entity.PowerUpEntity;
import entity.boss.Boss;
import entity.boss.ShieldSegment;
import entity.enemy.Enemy;
import entity.Player;
import entity.projectiles.EnemyProjectile;
import entity.projectiles.Projectile;
import graphics.Background;
import lib.GameLib;
import strategy.spawn.BossSpawner;
import strategy.spawn.EnemySpawner;
import strategy.spawn.EntitySpawner;
import strategy.spawn.PowerUpSpawner;
import util.State;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
		private final Player player;
		private final List<Enemy> enemies;
		private final List<Projectile> projectiles;
		private final List<Projectile> enemyProjectiles;
		private final List<PowerUpEntity> powerUps;
		private final List<Boss> bosses;
		private final Background background1;
		private final Background background2;

		private final EntitySpawner<Enemy> enemySpawner;
		private final EntitySpawner<Boss> bossSpawner;
		private final EntitySpawner<PowerUpEntity> powerUpSpawner;

		/* Indica que o jogo está em execução */
		private boolean running;
		private long currentTime;

		public Game() {
			this.player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
			this.projectiles = new ArrayList<>();
			this.enemies = new ArrayList<>();
			this.bosses = new ArrayList<>();
			this.enemyProjectiles = new ArrayList<>();
			this.powerUps = new ArrayList<>();
			this.background1 = new Background(20, Color.GRAY, 0.070, 3);
			this.background2 = new Background(50, Color.DARK_GRAY, 0.045, 2);
			this.running = true;
			this.currentTime = System.currentTimeMillis();

			this.enemySpawner = new EnemySpawner(currentTime);
			this.bossSpawner = new BossSpawner(currentTime);
			this.powerUpSpawner = new PowerUpSpawner(currentTime);
		}

		public void run() {
			/* iniciado interface gráfica */
			GameLib.initGraphics();
			//lib.GameLib.initGraphics_SAFE_MODE();  // chame esta versão do método caso nada seja desenhado na janela do jogo.

			/*************************************************************************************************/
			/*                                                                                               */
			/* Main loop do jogo                                                                             */
			/* -----------------                                                                             */
			/*                                                                                               */
			/* O main loop do jogo executa as seguintes operações:                                           */
			/*                                                                                               */
			/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
			/*                                                                                               */
			/* 2) Atualiza estados dos elementos baseados no tempo que correu entre a última atualização     */
			/*    e o timestamp atual: posição e orientação, execução de disparos de projéteis, etc.         */
			/*                                                                                               */
			/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
			/*                                                                                               */
			/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
			/*                                                                                               */
			/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
			/*                                                                                               */
			/*************************************************************************************************/
			while (running) {
				/* variáveis usadas no controle de tempo efetuado no main loop */

				/* Usada para atualizar o estado dos elementos do jogo    */
				/* (player, projéteis e inimigos) "delta" indica quantos  */
				/* ms se passaram desde a última atualização.             */
				long delta = System.currentTimeMillis() - currentTime;

				/* Já a variável "currentTime" nos dá o timestamp atual.  */
				currentTime = System.currentTimeMillis();

				update(delta);
				render(delta);

				/* chamada a display() da classe lib.GameLib atualiza o desenho exibido pela interface do jogo. */
				GameLib.display();

				/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
				busyWait(currentTime + 3);
			}

			System.exit(0);
		}

		/***************************/
		/* Atualizações de estados */
		/***************************/
		private void update(long delta) {
			player.update(delta, currentTime, projectiles);

			for (Projectile p : projectiles) {
				p.update(delta);
			}

			for (Enemy enemy : enemies) {
				enemy.update(delta, currentTime, enemyProjectiles, player);
			}

			for (Projectile p : enemyProjectiles) {
				p.update(delta);
			}

			for (PowerUpEntity p : powerUps) {
				p.update(delta, currentTime);
			}

			for (Boss boss : bosses) {
				boss.update(delta, currentTime);
			}

			checkCollisions();
			removeInactiveEntities();
			spawnEnemies();

			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
		}

		/*******************/
		/* Desenho da cena */
		/*******************/
		private void render(long delta) {
			background2.render(delta);
			background1.render(delta);

			for (Projectile p : projectiles) {
				p.render();
			}

			for (Projectile p : enemyProjectiles) {
				p.render();
			}

			for (Enemy e : enemies) {
				e.render(currentTime);
			}

			for (Boss b : bosses) {
				b.render(currentTime);
			}

			for (PowerUpEntity p : powerUps) {
				p.render(currentTime);
			}

			player.render(currentTime);
		}

		/***************************/
		/* Verificação de colisões */
		/***************************/
		private void checkCollisions() {

			if (player.getState() == State.ACTIVE) {
				if (!player.isInvincible()) {
					/* colisões player - projeteis (inimigo) */
					for (Projectile p : enemyProjectiles) {
						if (p.isActive() && player.collidesWith(p)) {
							player.explode(currentTime);
							break;
						}
					}

					/* colisões player - inimigos */
					for (Enemy e : enemies) {
						if (e.isActive() && player.collidesWith(e)) {
							player.explode(currentTime);
							break;
						}
					}

					/* colisões player - bosses */
					for (Boss b : bosses) {
						if (b.isActive() && (player.collidesWith(b) || b.getShields().stream().anyMatch(player::collidesWith))) {
							player.explode(currentTime);
							break;
						}
					}
				}

				/* colisões player - power ups coletaveis */
				for (PowerUpEntity pw : powerUps) {
					if (pw.isActive() && player.collidesWith(pw)) {
						player.addPowerUp(pw.getPowerUp());
						pw.setInactive();
						break;
					}
				}
			}

			/* colisões projeteis (player) - inimigos */
			for (Projectile p : projectiles) {
				for (Enemy e : enemies) {
					if (p.isActive() && e.isActive() && p.collidesWith(e)) {
						e.explode(currentTime);
						p.setInactive();
					}
				}
			}

			/* colisões projeteis (player) - bosses */
			for (Projectile p : projectiles) {
				for (Boss b : bosses) {
					if (p.isActive() && b.isActive() && p.collidesWith(b)) {
						b.explode(currentTime);
						p.setInactive();
					}

					for (ShieldSegment s : b.getShields()) {
						if (p.isActive() && b.isActive() && p.collidesWith(s)) {
							p.setInactive();

							Projectile deflected = new EnemyProjectile(p.getX(), p.getY(), p.getVX(), p.getVY(), Color.RED);
							deflected.deflect();
							enemyProjectiles.add(deflected);
						}
					}
				}
			}

			/* colisões projeteis (player) - power ups */
			for (Projectile p : projectiles) {
				for (PowerUpEntity pw : powerUps) {
					if (p.isActive() && pw.isActive() && p.collidesWith(pw)) {
						pw.setInactive();
						player.addPowerUp(pw.getPowerUp());
					}
				}
			}
		}

		private void removeInactiveEntities() {
			projectiles.removeIf(p -> !p.isActive());
			enemyProjectiles.removeIf(p -> !p.isActive());
			enemies.removeIf(e -> e.getState() == State.INACTIVE);
			powerUps.removeIf(p -> p.getState() == State.INACTIVE);
		}

		private void spawnEnemies() {
			bosses.addAll(bossSpawner.spawn(currentTime));
			powerUps.addAll(powerUpSpawner.spawn(currentTime));

			if (!bosses.isEmpty()) {
				for (Enemy e : enemies) {
					e.setInactive();
				}
			} else {
				enemies.addAll(enemySpawner.spawn(currentTime));
			}
		}

		/* Espera, sem fazer nada, até que o instante de tempo atual seja */
		/* maior ou igual ao instante especificado no parâmetro "time".    */
		private void busyWait(long time) {
			while(System.currentTimeMillis() < time) Thread.yield();
		}
	}