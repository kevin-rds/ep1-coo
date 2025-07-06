package game;


import entity.EntityType;
import entity.Player;
import game.config.*;
import game.config.spawn.boss.BossSpawnInfo;
import game.config.spawn.enemy.EnemySpawnInfo;
import game.config.spawn.powerup.PowerUpSpawnInfo;
import game.manager.CollisionManager;
import game.manager.EntityManager;
import game.manager.LifeManager;
import game.manager.RenderManager;
import graphics.Background;
import lib.GameLib;
import strategy.spawn.BossSpawner;
import strategy.spawn.EnemySpawner;
import game.context.GameContext;

import strategy.spawn.PowerUpSpawner;
import util.State;

import java.awt.Color;
import java.io.IOException;

public class Game {

	private final Background background1;
	private final Background background2;
	private final LifeManager lifeManager;
	private final RenderManager renderManager;
	private final CollisionManager collisionManager;
	private final EntityManager entityManager;

	private boolean running;
	private long currentTime;
	private final Player player;
	private final GameMode mode;

	private int currentLevelIndex;
	private long levelStartTime;
	private boolean bossSpawnedThisLevel;

	public Game(GameMode mode) {
		this.mode = mode;
		this.running = true;
		this.currentTime = System.currentTimeMillis();

		this.background1 = new Background(20, Color.GRAY, 0.070, 3);
		this.background2 = new Background(50, Color.DARK_GRAY, 0.045, 2);
		this.player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90);

		this.entityManager = new EntityManager(player);
		this.renderManager = new RenderManager();

		LifeManager lm;
		if (this.mode == GameMode.STORY) {
			try {
				ConfigLoader.loadGameConfig("configfiles/config.txt");
				lm = new LifeManager(ConfigLoader.playerLives);
				this.currentLevelIndex = 0;
				startNextLevel();
			} catch (IOException e) {
				System.err.println("Erro ao carregar configuração do jogo: " + e.getMessage());
				e.printStackTrace();
				this.running = false;
				lm = new LifeManager(0);
			}
		} else {
			lm = new LifeManager(3);
			this.entityManager.setInfiniteModeSpawners(
					new EnemySpawner(currentTime), null
			);
		}
		this.lifeManager = lm;
		this.collisionManager = new CollisionManager(this.lifeManager);
	}

	public void run() {
		/* iniciado interface gráfica */
		GameLib.initGraphics();
		//lib.GameLib.initGraphics_SAFE_MODE();  // chame esta versão do metodo caso nada seja desenhado na janela do jogo.

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

			GameContext context = new GameContext(
					player, background1, background2, lifeManager,
					entityManager.getEnemies(), entityManager.getBosses(),
					entityManager.getProjectiles(), entityManager.getEnemyProjectiles(),
					entityManager.getPowerUps(),
					entityManager.getAllEntities(),
					currentTime, delta
			);


			update(context);
			renderManager.render(context);

			/* chamada a display() da classe lib.GameLib atualiza o desenho exibido pela interface do jogo. */
			GameLib.display();

			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			busyWait(currentTime + 3);
		}

		System.exit(0);
	}

	private void update(GameContext context) {

		if (player.getState() == State.INACTIVE) {
			if (!lifeManager.isGameOver()) {
				player.respawn(this.currentTime);
			} else {
				this.running = false;
				System.out.println("Game Over!");
			}
		}

		entityManager.updateAll(context);
		collisionManager.checkCollisions(context);
		entityManager.cleanupAll();

		if (mode == GameMode.STORY && bossSpawnedThisLevel && entityManager.getBosses().isEmpty()) {
			currentLevelIndex++;
			startNextLevel();
		} else {
			boolean isBossActive = !entityManager.getBosses().isEmpty();
			if (entityManager.spawnAll(currentTime, isBossActive, mode)) {
				bossSpawnedThisLevel = true;
			}
		}

		if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
	}

	private void startNextLevel() {
		if (currentLevelIndex >= ConfigLoader.levels.size()) {
			System.out.println("VOCE VENCEU O JOGO!");
			running = false;
			return;
		}

		this.levelStartTime = this.currentTime;
		this.bossSpawnedThisLevel = false;
		Level currentLevel = ConfigLoader.levels.get(currentLevelIndex);
		System.out.println("Iniciando fase: " + currentLevel.getConfigFileName());

		entityManager.setStoryModeSpawners(
				new EnemySpawner(currentLevel.getSpawnList(EntityType.ENEMY, EnemySpawnInfo.class), levelStartTime),
				new BossSpawner(currentLevel.getSpawnList(EntityType.BOSS, BossSpawnInfo.class), levelStartTime),
				new PowerUpSpawner(currentLevel.getSpawnList(EntityType.POWERUP, PowerUpSpawnInfo.class), levelStartTime)
		);
	}

		/* Espera, sem fazer nada, até que o instante de tempo atual seja */
		/* maior ou igual ao instante especificado no parâmetro "time".    */
		private void busyWait(long time) {
			while(System.currentTimeMillis() < time) Thread.yield();
		}
	}