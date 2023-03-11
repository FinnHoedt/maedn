package maedn;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;





public class GUI extends Application{
	
	Boolean verwaltungsmodus_bool=false;
	Boolean neue_playlist_bool=false;
	Boolean isRunning_bool=false;
	Boolean isPlaying_bool = false;
	Boolean playlist_edit_scene_bool=false;
	ListView<String> list_mp3, playlist_edit_lv, edit_songs_tv;
	Media media;
	MediaPlayer mediaPlayer;
	String titel_str, genre_str, interpret_str;
	double current_song_time = 0;
	
	Controller controller = new Controller();
	Playlist playlist, current_playlist;
	ArrayList<Song> all, current_songs_songs_tb;
	Song current_song;
	
	Scene benutzermodus, verwaltungsmodus, neue_playlist_scene, playlist_edit_scene;
	Pane flaeche,flaeche2, flaeche3, unten_pn, playlisten_pn, edit_pn;
	HBox suchleiste_hb, kategorien_hb, buttons_hb;
	VBox playlisten_vb, add_vb, neue_playlist_vb;
	ScrollPane playlisten_sp;
	Label maedn, titel_l2, interpret_l2, genre_l2, titel_l, interpret_l, genre_l, currentTitel_l, currentInterpret_l, currentGenre_l, neue_playlist_name_l, current_time_l, end_time_l;
	Button suchen_bt, verwaltungsmodus_bt, neue_playlist_bt, allsongs_playlist_bt, save_bt, play_bt, next_bt, previous_bt, neue_playlist_save_bt, addImg_bt; 
	MenuButton filter, song_ppp_mb;
	CheckBox neue_playlist_songs_cb;
	MenuItem menu_titel1, menu_titel2, menu_titel3;
	TableView<Song> songs_tb;
	TableColumn<Song, String> titel_cl, interpret_cl, genre_cl;
	FileInputStream inputStream_font, inputStream_lupe, inputStream_plus, inputStream_schraubenzieher, inputStream_nasenaffe, inputStream_stift,
		inputStream_noten, inputStream_meadn_icon, inputStream_button;
	ImageView view_schraubenzieher, view_lupe, view_plus, view_nasenaffe, view_noten, play_view, previous_view, next_view, pause_view, stift_view;
	Image lupe, plus, schraubenzieher, nasenaffe, noten, play, previous, next, pause, img, stift;
    TextField suche_tf, titel_tf, interpret_tf, genre_tf, neue_playlist_name_tf;
    Line line;
    Rectangle unten_grau, links_orange, white_rec, gray_img_rec;
    Font font_nl_25, font_nli_20, font_nb_50, font_nl_20, font_nb_10;
    Color orange, orange_light;
    Stage neue_playlist_stage, verwaltung_stage, playlist_edit_stage ;
    Timer timer;
    TimerTask task;
    ProgressBar songProgressBar;
    Slider song_progress_sl;
    File selectedImg;
	@Override
	public void start(Stage stage) throws Exception {
	
		controller.initialize();
		playlist = controller.getPlaylist();
		all = playlist.getPlaylist();
		current_songs_songs_tb = all;
		
		flaeche = new Pane();
		
		BackgroundFill bgf = new BackgroundFill(Color.web("#2c2f33"), null, null);
		flaeche.setBackground(new Background(bgf)); 
		flaeche.setMinSize(900, 700);
		
		inputStream_lupe = new FileInputStream("images/lupe.png");
		lupe = new Image(inputStream_lupe);
		view_lupe = new ImageView(lupe);
		view_lupe.setFitHeight(27);
		view_lupe.setFitWidth(27);
		
		inputStream_font = new FileInputStream("fonts/Nexa_Bold.otf");
		font_nb_50 = Font.loadFont(inputStream_font, 50);
		
		maedn = new Label("Maedn");
		orange = Color.web("#f2ac41");
		maedn.setTextFill(orange);
		maedn.setFont(font_nb_50);
		maedn.relocate(717, 9);;

		titel_str = "Titel";
		interpret_str = "Interpret";
		genre_str = "Genre";
		menu_titel1 = new MenuItem(titel_str);
		menu_titel2 = new MenuItem(interpret_str);
		menu_titel3 = new MenuItem(genre_str);
		filter = new MenuButton(titel_str,null,menu_titel1,menu_titel2,menu_titel3);
		filter.relocate(100, 60);
		
		menu_titel1.setOnAction(e->{filter.setText(titel_str);});
		menu_titel2.setOnAction(e->{filter.setText(interpret_str);});
		menu_titel3.setOnAction(e->{filter.setText(genre_str);});
			
		suchen_bt = new Button();
		suchen_bt.setPrefSize(30,30);
		suchen_bt.setGraphic(view_lupe);
		suchen_bt.setOnAction(e->{pressSuchen();});
			
		suche_tf = new TextField("Suche");
		suche_tf.setPrefHeight(36);
		suche_tf.setPrefWidth(250);
		suche_tf.setOnMousePressed(e->{if(suche_tf.getText().equals("Suche")) 
        {suche_tf.setText("");}});
		suche_tf.setOnKeyReleased(e->{pressSuchen();});

		suchleiste_hb = new HBox(2);
		suchleiste_hb.relocate(100, 20);
		suchleiste_hb.getChildren().addAll(suche_tf,suchen_bt);
		
		links_orange = new Rectangle(90,700);
		links_orange.setFill(Color.web("#f2ac41"));
		
		playlisten_sp = new ScrollPane();
        playlisten_sp.setMaxHeight(510);
        playlisten_sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        playlisten_sp.setOnMouseEntered(e->{playlisten_sp.setVbarPolicy(ScrollBarPolicy.NEVER);});
        playlisten_sp.setOnMouseExited(e->{playlisten_sp.setVbarPolicy(ScrollBarPolicy.NEVER);});
        playlisten_sp.relocate(12, 100);

        playlisten_vb = new VBox();
        neue_playlist_bt = new Button();
        allsongs_playlist_bt = new Button();
        playlisten_pn = new Pane();
        inputStream_plus = new FileInputStream("images/plus.png");
        plus = new Image(inputStream_plus);
        view_plus = new ImageView(plus);
        view_plus.setFitHeight(50);
        view_plus.setFitWidth(50);
        inputStream_noten = new FileInputStream("images/noten.png");
        noten = new Image(inputStream_noten);
        view_noten = new ImageView(noten);
        view_noten.setFitHeight(50);
        view_noten.setFitWidth(50);
        neue_playlist_bt.setGraphic(view_plus);
        neue_playlist_bt.setOnAction(e->{pressNeuePlaylist();});
        neue_playlist_bt.setTooltip(new Tooltip("neue Playlist erstellen"));
        neue_playlist_bt.relocate(13, 20);
        allsongs_playlist_bt.setGraphic(view_noten);
        allsongs_playlist_bt.setPrefSize(66,66);
        allsongs_playlist_bt.relocate(13, 85);
        allsongs_playlist_bt.setOnAction(e->{fillSongTabelle(all); current_songs_songs_tb = all;});
        allsongs_playlist_bt.setTooltip(new Tooltip("All Songs"));
        playlisten_vb.getChildren().addAll(allsongs_playlist_bt);
        playlisten_vb.setSpacing(10);
        playlisten_vb.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        playlisten_sp.setContent(playlisten_vb);
        playlisten_sp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        playlisten_pn.getChildren().addAll(neue_playlist_bt,playlisten_sp);
        playlisten_pn.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
             
        
		verwaltungsmodus_bt = new Button();
		inputStream_schraubenzieher = new FileInputStream("images/schraubenzieher.png");
		schraubenzieher = new Image(inputStream_schraubenzieher);
		view_schraubenzieher = new ImageView(schraubenzieher);
		view_schraubenzieher.setFitHeight(50);
		view_schraubenzieher.setFitWidth(50);
		verwaltungsmodus_bt.setGraphic(view_schraubenzieher);
		verwaltungsmodus_bt.relocate(13, 630);
		verwaltungsmodus_bt.setOnAction(e->{openVerwaltungsModusWindow();});
		verwaltungsmodus_bt.setTooltip(new Tooltip("Verwaltungsmodus"));
		
		unten_pn = new Pane();
		
		unten_grau = new Rectangle(815,230);
		unten_grau.setFill(Color.web("#23272a"));
		unten_grau.relocate(90, 700-230);
		
		inputStream_font = new FileInputStream("fonts/Nexa_Light.otf");
		font_nl_25 = Font.loadFont(inputStream_font, 25);
		inputStream_font = new FileInputStream("fonts/Nexa_Light.otf");
		font_nl_20 = Font.loadFont(inputStream_font, 20);
		inputStream_font = new FileInputStream("fonts/NexaLightItalic.otf");
		font_nli_20 = Font.loadFont(inputStream_font, 20);
				
		currentTitel_l = new Label("Titel");
		currentTitel_l.relocate(unten_grau.getLayoutX() + 375, unten_grau.getLayoutY() + 13);
		currentTitel_l.setFont(font_nl_25);
		currentTitel_l.setTextFill(Color.WHITE);
		currentInterpret_l = new Label("Interpret");
		currentInterpret_l.relocate(unten_grau.getLayoutX() + 375, unten_grau.getLayoutY() + 45);
		currentInterpret_l.setFont(font_nl_20);
		currentInterpret_l.setTextFill(Color.GREY);
		currentGenre_l = new Label("Genre");
		currentGenre_l.relocate(unten_grau.getLayoutX() + 375, unten_grau.getLayoutY() + 100);
		currentGenre_l.setFont(font_nli_20);
		currentGenre_l.setTextFill(Color.LIGHTGREY);
		
		gray_img_rec = new Rectangle((unten_grau.getWidth()/2.3),unten_grau.getHeight());
        gray_img_rec.relocate((unten_grau.getLayoutX()), unten_grau.getLayoutY());
        gray_img_rec.setFill(Color.BLACK);
        
        
		song_ppp_mb = new MenuButton("Hinzuf\u00fcgen zu",null);
		song_ppp_mb.relocate(unten_grau.getLayoutX() + 650, unten_grau.getLayoutY() + 100);
		
        song_progress_sl = new Slider();
		song_progress_sl.setPrefWidth(320);
		song_progress_sl.relocate(unten_grau.getLayoutX() + 440, unten_grau.getLayoutY() + 150);
		song_progress_sl.setOnMouseClicked(e->{songSliderMoved();});
		songProgressBar = new ProgressBar();
		songProgressBar.setPrefWidth(320);
		songProgressBar.relocate(unten_grau.getLayoutX() + 440, unten_grau.getLayoutY() + 150);
		current_time_l = new Label("0:00");
        current_time_l.relocate(song_progress_sl.getLayoutX() - 30, song_progress_sl.getLayoutY());
        current_time_l.setTextFill(Color.WHITE);
        end_time_l = new Label("0:00");
        end_time_l.relocate(song_progress_sl.getLayoutX() + 330, song_progress_sl.getLayoutY()); 
        end_time_l.setTextFill(Color.WHITE);
        
        
		unten_pn.getChildren().addAll(unten_grau,currentTitel_l,currentInterpret_l, currentGenre_l, song_progress_sl, gray_img_rec, current_time_l, end_time_l, song_ppp_mb);
		
		
		
		kategorien_hb = new HBox();
		
		orange_light = Color.web("#fac26b");
		titel_l = new Label(titel_str);
		titel_l.setTextFill(orange_light);
		titel_l.setFont(font_nl_25);
		interpret_l = new Label(interpret_str);
		interpret_l.setTextFill(orange_light);
		interpret_l.setFont(font_nl_25);
		genre_l = new Label(genre_str);
		genre_l.setTextFill(orange_light);
		genre_l.setFont(font_nl_25);
		kategorien_hb.relocate(180, 100);
		kategorien_hb.setSpacing(200);
		kategorien_hb.getChildren().addAll(titel_l,interpret_l,genre_l);
		
		line = new Line(120,135,870,135);
		line.setStroke(orange_light);
		line.setStrokeWidth(3);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
				
		songs_tb = new TableView<Song>();
		titel_cl = new TableColumn<Song, String>();
		titel_cl.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
		interpret_cl = new TableColumn<Song, String>();
		interpret_cl.setCellValueFactory(new PropertyValueFactory<Song, String>("interpret"));
		genre_cl = new TableColumn<Song, String>();
		genre_cl.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
		songs_tb.getColumns().addAll(titel_cl,interpret_cl,genre_cl);
		songs_tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		songs_tb.setPrefSize(750, 300);
		songs_tb.relocate(119, 140);
		songs_tb.setStyle("-fx-background-color: #2c2f33;");
		
		
		buttons_hb = new HBox();
		play_bt = new Button();
		inputStream_button = new FileInputStream("images/play_icon.png");
		play = new Image(inputStream_button);
		play_view = new ImageView(play);
		play_view.setFitHeight(25);
		play_view.setFitWidth(25);
		play_bt.setGraphic(play_view);
		play_bt.setOnAction(e->{pressPlayPause();});
		next_bt = new Button();
		inputStream_button = new FileInputStream("images/next_icon.png");
		next = new Image(inputStream_button);
		next_view = new ImageView(next);
		next_view.setFitHeight(25);
		next_view.setFitWidth(25);
		next_bt.setGraphic(next_view);
		next_bt.setOnAction(e->{pressNextButton();});
		previous_bt = new Button();
		inputStream_button = new FileInputStream("images/previous.png");
		previous = new Image(inputStream_button);
		previous_view = new ImageView(previous);
		previous_view.setFitHeight(25);
		previous_view.setFitWidth(25);
		previous_bt.setGraphic(previous_view);
		previous_bt.setOnAction(e->{pressPreviousButton();});
		buttons_hb.getChildren().addAll(previous_bt, play_bt,next_bt);
		buttons_hb.setSpacing(15);
		buttons_hb.relocate(610, 645);
		
		fillSongTabelle(all);
		loadPlaylistButtons();
		hinzufuegenZu();
		
		songs_tb.setOnMouseClicked(e->{onClickRow(); hinzufuegenZu();});
		
		stage.setOnCloseRequest(e->{stopMusic();});
		
		flaeche.getChildren().addAll(line, unten_pn, links_orange,suchleiste_hb, kategorien_hb, maedn, filter, songs_tb, playlisten_pn, 
				verwaltungsmodus_bt, buttons_hb);
	
		changeBackgroundOnHoverGray(suchen_bt);
		changeBackgroundOnHoverGray(play_bt);
		changeBackgroundOnHoverGray(next_bt);
		changeBackgroundOnHoverGray(previous_bt);
		
		changeBackgroundOnHoverOrange(verwaltungsmodus_bt);
		changeBackgroundOnHoverOrange(neue_playlist_bt);
		changeBackgroundOnHoverOrange(allsongs_playlist_bt);
		
		
		benutzermodus = new Scene(flaeche);
		stage.setOnCloseRequest(e->{stopMusic();});
		stage.setTitle("Maedn");
		stage.setScene(benutzermodus);
		stage.setResizable(false);
		inputStream_meadn_icon = new FileInputStream("images/maedn_icon.png");
		stage.getIcons().add(new Image(inputStream_meadn_icon));
		stage.show();
	}
	
	private void changeBackgroundOnHoverGray(Node node) {
	    node.styleProperty().bind(
	      Bindings
	        .when(node.hoverProperty())
	          .then(
	            new SimpleStringProperty("-fx-background-color: #494c52;")
	          )
	          .otherwise(
	            new SimpleStringProperty("-fx-background-color: transparent;")
	          )
	    );
	  }
	private void changeBackgroundOnHoverOrange(Node node) {
	    node.styleProperty().bind(
	      Bindings
	        .when(node.hoverProperty())
	          .then(
	            new SimpleStringProperty("-fx-background-color: #ffd780; -fx-border-color:#424242; -fx-border-width:0px; -fx-border-radius: 18; -fx-background-radius: 18;")
	          )
	          .otherwise(
	            new SimpleStringProperty("-fx-background-color: #f7be54; -fx-border-color:#424242; -fx-border-width:0px; -fx-border-radius: 18; -fx-background-radius: 18;")
	          )
	    );
	  }
	
	public void openVerwaltungsModusWindow() {
		flaeche2 = new Pane();
	    flaeche2.setMinSize(900, 500);
	    BackgroundFill bgf = new BackgroundFill(Color.web("#2c2f33"), null, null);
	    flaeche2.setBackground(new Background(bgf));
	    
	    list_mp3 = new ListView<String>();
	    list_mp3.relocate(50, 50);
	    list_mp3.setOnMouseClicked(e->{loadTextIntoField();});
		
		ArrayList<Song> songs = controller.getAllSongList();
		for(Song song: songs) {
			list_mp3.getItems().add(song.getFile().getName());
		}

		add_vb = new VBox();
	    titel_l2 = new Label(titel_str);
	    titel_l2.setTextFill(orange);
	    interpret_l2 = new Label(interpret_str);
	    interpret_l2.setTextFill(orange);
	    genre_l2 = new Label(genre_str);
	    genre_l2.setTextFill(orange);
	    titel_tf = new TextField();
	    interpret_tf = new TextField();
	    genre_tf = new TextField();
	    save_bt = new Button("Speichern");
	    add_vb.setSpacing(10);
	    
	    
	    add_vb.getChildren().addAll(titel_l2, titel_tf, interpret_l2, interpret_tf, genre_l2, genre_tf, save_bt);
	    add_vb.relocate(350, 50);
	    
	    white_rec = new Rectangle(250,150);
	    white_rec.setFill(Color.BLACK);
	   	white_rec.relocate(550, 80);
	    addImg_bt = new Button("Bild hinzufuegen");
	    addImg_bt.relocate(550, 238);
	    addImg_bt.setOnAction(e->{white_rec.setFill(new ImagePattern(chooseImg()));});
	    
	    save_bt.setOnAction(e->{saveMP3();});
	    
	    flaeche2.getChildren().addAll(list_mp3, add_vb, addImg_bt, white_rec);

	    verwaltungsmodus = new Scene(flaeche2);
	    verwaltung_stage = new Stage();
	    verwaltung_stage.setScene(verwaltungsmodus);
	    verwaltung_stage.setTitle("Verwaltungsmodus");
	    verwaltung_stage.setResizable(false);
	    try {
			inputStream_meadn_icon = new FileInputStream("images/maedn_icon.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    verwaltung_stage.getIcons().add(new Image(inputStream_meadn_icon));
	    verwaltung_stage.setOnCloseRequest(e->{verwaltungsmodus_bool=false; fillSongTabelle(all);});
	    if(!verwaltungsmodus_bool) {
	        verwaltung_stage.show();
	        verwaltungsmodus_bool = true;
	    }
	}
	
	public Image chooseImg() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("*.jpg", "*.png"));
		selectedImg = fc.showOpenDialog(null);
		if(selectedImg != null) {
			String path = selectedImg.toPath().toString();
			FileInputStream img_fis = null;
			try {
				img_fis = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			img = new Image(img_fis);
			return img;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("images/BLACK.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img = new Image(fis);
		return img;
	}
	
	public void loadTextIntoField() {
		playlist = controller.getPlaylist();
		all = playlist.getPlaylist();
		int index = list_mp3.getSelectionModel().getSelectedIndex();
		titel_tf.setText(all.get(index).getName());
		interpret_tf.setText(all.get(index).getInterpret());
		genre_tf.setText(all.get(index).getGenre());
		
		if(all.get(index).getImage() != null) {
			String path = all.get(index).getImage().toPath().toString();
			FileInputStream img_fis = null;
			try {
				img_fis = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Image currentImg = new Image(img_fis);
			white_rec.setFill(new ImagePattern(currentImg));
		} else {
			white_rec.setFill(Color.BLACK);
		}
	}
	
	public void pressNeuePlaylist() {
		flaeche3 = new Pane();
	    flaeche3.setMinSize(300, 150);
	    BackgroundFill bgf = new BackgroundFill(Color.web("#2c2f33"), null, null);
	    flaeche3.setBackground(new Background(bgf));
	    neue_playlist_name_l = new Label("Name:");
	    neue_playlist_name_l.setTextFill(orange);
	    neue_playlist_name_tf = new TextField("Neue Playlist");
	    neue_playlist_save_bt = new Button("Speichern");
	    
	    neue_playlist_save_bt.setOnAction(e->{createNewPlaylist();});
	    neue_playlist_songs_cb = new CheckBox("Angezeigte Songs waehlen");
	    neue_playlist_songs_cb.setSelected(false);
	    neue_playlist_songs_cb.setTextFill(Color.WHITE);
	    neue_playlist_vb = new VBox();
	    neue_playlist_vb.setSpacing(10);
	    neue_playlist_vb.relocate(70, 15);
	    neue_playlist_vb.getChildren().addAll(neue_playlist_name_l, neue_playlist_name_tf,neue_playlist_songs_cb, neue_playlist_save_bt  );
	    flaeche3.getChildren().addAll(neue_playlist_vb);
	    
	    neue_playlist_scene = new Scene(flaeche3);
	    neue_playlist_stage = new Stage();
	    neue_playlist_stage.setScene(neue_playlist_scene); 
	    neue_playlist_stage.setTitle("Neue Playlist");
	    neue_playlist_stage.setResizable(false);
	    try {
			inputStream_meadn_icon = new FileInputStream("images/maedn_icon.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    neue_playlist_stage.getIcons().add(new Image(inputStream_meadn_icon));
	    neue_playlist_stage.setOnCloseRequest(e->{neue_playlist_bool=false;}); 
	    if(!neue_playlist_bool) {
	    	neue_playlist_stage.show();
	        neue_playlist_bool = true;
	    }
	    
	   
	}
	
	public void createNewPlaylist() {
		
		current_playlist = new Playlist(neue_playlist_name_tf.getText());
		if(neue_playlist_songs_cb.isSelected() && current_songs_songs_tb != all) {
			current_playlist.setPlaylist(current_songs_songs_tb);;
		} else {
			current_playlist.setPlaylist(new ArrayList<Song>());
		}

		newPlaylistButton(current_playlist);
		
		neue_playlist_stage.close();
		neue_playlist_bool = false;
	}
	
	public void newPlaylistButton(Playlist playlist) {
		HBox playlist_hb = new HBox();
		Button playlist_bt = new Button(playlist.getPlaylistName());
		Playlist playlist_neu = new Playlist(playlist.getPlaylistName());
		Button playlist_edit_bt = new Button();
		try {
			inputStream_stift = new FileInputStream("images/stift.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		playlist_bt.setStyle("-fx-background-color: #ff0000;");
		changeBackgroundOnHoverOrange(playlist_edit_bt);
		changeBackgroundOnHoverOrange(playlist_bt);
		
		playlist_neu.setPlaylist(playlist.getPlaylist());
		playlist_bt.setPrefSize(66, 66);
		try {
			inputStream_font = new FileInputStream("fonts/Nexa_Bold.otf");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		font_nb_10 = Font.loadFont(inputStream_font, 12);
		playlist_bt.setFont(font_nb_10);
		stift = new Image(inputStream_stift);
		stift_view = new ImageView(stift);
		stift_view.setFitHeight(25);
		stift_view.setFitWidth(25);
	
		
		playlist_edit_bt.setGraphic(stift_view);
		playlist_edit_bt.setOnAction(e->{pressPlaylistEdit(playlist_neu, playlist_hb);});
		playlist_hb.getChildren().addAll(playlist_bt);
		playlist_hb.setOnMouseEntered(e->{playlist_hb.getChildren().add(playlist_edit_bt); hoverPlaylist(playlist_hb); });
		playlist_hb.setOnMouseExited(e->{hoverExitPlaylist(playlist_hb);});
		playlist_bt.setOnAction(e->{fillSongTabelle(playlist_neu.getPlaylist()); current_songs_songs_tb = playlist_neu.getPlaylist();});
		
		playlisten_vb.getChildren().addAll(playlist_hb);
		controller.addPlaylist(playlist_neu);
		hinzufuegenZu();
		
	}
	
	public void hoverPlaylist(HBox playlist_hb) {
		Button playlist_bt = (Button) playlist_hb.getChildren().get(0);
		Button playlist_edit_bt = (Button) playlist_hb.getChildren().get(1);
		playlist_bt.setPrefSize(200,66);
		playlist_edit_bt.setMaxSize(30, 66);
		}
	
	public void hoverExitPlaylist(HBox playlist_hb) {
		Button playlist_bt = (Button) playlist_hb.getChildren().get(0);
		Button playlist_edit_bt = (Button) playlist_hb.getChildren().get(1);
		playlist_bt.setPrefSize(66,66);
		playlist_hb.getChildren().remove(playlist_edit_bt);
	}
	
	public void pressPlaylistEdit(Playlist playlist, HBox playlist_hb) {
		
		edit_songs_tv = new ListView<>();
	    edit_songs_tv.relocate(0, 100);
	    edit_songs_tv.setMaxSize(300, 200);
	    
		for(int i=0; i<playlist.getPlaylist().size(); i++) {
			edit_songs_tv.getItems().add(playlist.getPlaylist().get(i).getName());
		}
	    TextField playlist_edit_name_tf = new TextField(playlist.getPlaylistName());
	    playlist_edit_name_tf.setFont(font_nb_50);
	    playlist_edit_name_tf.setMaxWidth(460);
	    Button playlist_edit_remove_song_bt = new Button("Song entfernen");
	    playlist_edit_remove_song_bt.setOnAction(e->{playlistEditRemoveSong(playlist, edit_songs_tv.getSelectionModel().getSelectedIndex());});
	    playlist_edit_remove_song_bt.relocate(edit_songs_tv.getLayoutX() + edit_songs_tv.getMaxWidth(), edit_songs_tv.getLayoutY() + 40);
	    Button playlist_edit_save_bt = new Button("Speichern");
	    playlist_edit_save_bt.setOnAction(e->{playlistEditSaveButton(playlist, playlist_hb, playlist_edit_name_tf.getText());});
	    playlist_edit_save_bt.relocate(playlist_edit_remove_song_bt.getLayoutX(), playlist_edit_remove_song_bt.getLayoutY() + 100);
	    Button playlist_edit_delete_bt = new Button("Playlist l\u00f6schen");
	    playlist_edit_delete_bt.setOnAction(e->{playlistEditDeleteButton(playlist, playlist_hb);});
	    playlist_edit_delete_bt.relocate( playlist_edit_remove_song_bt.getLayoutX(), playlist_edit_remove_song_bt.getLayoutY() + 50);
	    Pane playlist_edit_name_pn = new Pane();
	    playlist_edit_name_pn.relocate(20,20);
	   
	    
	    
	   
		playlist_edit_name_pn.getChildren().addAll(playlist_edit_name_tf, playlist_edit_save_bt, playlist_edit_delete_bt, edit_songs_tv, playlist_edit_remove_song_bt);
	   
	    edit_pn = new Pane();
	    edit_pn.setMinSize(500, 400);
	    BackgroundFill bgf = new BackgroundFill(Color.web("#2c2f33"), null, null);
	    edit_pn.setBackground(new Background(bgf));
	    edit_pn.getChildren().addAll(playlist_edit_name_pn);
	    playlist_edit_scene = new Scene(edit_pn);
	    playlist_edit_stage = new Stage();
	    playlist_edit_stage.setScene(playlist_edit_scene);
	    playlist_edit_stage.setTitle("Edit Playlist " + playlist.getPlaylistName());
	    playlist_edit_stage.setResizable(false);
	    playlist_edit_stage.setOnCloseRequest(e->{playlist_edit_scene_bool=false; });
	    try {
			inputStream_meadn_icon = new FileInputStream("images/maedn_icon.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    playlist_edit_stage.getIcons().add(new Image(inputStream_meadn_icon));
	    if(!playlist_edit_scene_bool) {
	        playlist_edit_stage.show();
	        playlist_edit_scene_bool = true;
	    }
	}
	
	public void playlistEditRemoveSong(Playlist playlist, int index) {
		if(index >= 0) {
				edit_songs_tv.getItems().clear();
				playlist.getPlaylist().remove(index);
				for(int i=0; i<playlist.getPlaylist().size(); i++) {
					edit_songs_tv.getItems().add(playlist.getPlaylist().get(i).getName());
				}
		}
		fillSongTabelle(playlist.getPlaylist());
	}
	
	public void playlistEditSaveButton(Playlist playlist, HBox playlist_hb, String name) {
		playlist.setPlaylistName(name);
	    Button test_bt = (Button) playlist_hb.getChildren().get(0);
	    test_bt.setText(name);
	    playlist_hb.getChildren().remove(0);
	    playlist_hb.getChildren().add(0, test_bt);
	    playlist_edit_scene_bool = false;
	    playlist_edit_stage.close();
	    hinzufuegenZu();
	}
	
	public void playlistEditDeleteButton(Playlist playlist, HBox playlist_hb) {
		playlisten_vb.getChildren().remove(playlist_hb);
		controller.removePlaylist(playlist);
		playlist_edit_scene_bool = false;
		playlist_edit_stage.close();
		hinzufuegenZu();
		fillSongTabelle(Controller.all.getPlaylist());
		current_songs_songs_tb = Controller.all.getPlaylist();
	}
	
	public void hinzufuegenZu() {
		song_ppp_mb.getItems().clear();
		for(int i=0; i<controller.getPlaylists().size(); i++) {
			final int j = i;
			MenuItem playlist_mi = new MenuItem(controller.getPlaylists().get(i).getPlaylistName());
			song_ppp_mb.getItems().addAll(playlist_mi);
			if(songs_tb.getSelectionModel().getSelectedItem() != null) {
				Song current_song = current_songs_songs_tb.get(songs_tb.getSelectionModel().getSelectedIndex());
				if(!istSongEingefuegt(controller.getPlaylists().get(j), current_song)) {
					playlist_mi.setOnAction(e->{controller.getPlaylists().get(j).addSong(current_song); });
				}
			}
		}	
	}
	
	public boolean istSongEingefuegt(Playlist playlist, Song current_song) {
		for(Song i : playlist.getPlaylist()) {
			if(controller.isEqual(i.getFile().toPath(), current_song.getFile().toPath())) {
				return true;
			}
		}
		return false;
	}
	
	public void loadPlaylistButtons() {
		ArrayList<Playlist> playlists = controller.getPlaylists();
		for(int i=0; i<playlists.size(); i++) {
			newPlaylistButton(playlists.get(0));
			controller.removePlaylist(controller.getPlaylists().get(0));
		}
		
	}

	public void saveMP3() {
	    int index = list_mp3.getSelectionModel().getSelectedIndex();
	    String titel = titel_tf.getText();
	    String interpret = interpret_tf.getText();
	    String genre = genre_tf.getText();
	    File image = selectedImg;

	   Controller.all.changeSong(index, titel, interpret, genre, image);
	}
	
	public void fillSongTabelle(ArrayList<Song> list) {
		songs_tb.getItems().clear();
		if(!list.isEmpty()) {
			for(Song song : list ) {
			songs_tb.getItems().add(song);		
			}
		}

	}
	
	public void pressPlayPause() {
		if(current_song != null) {
			if(!isPlaying_bool) {
				isPlaying_bool = true;
				changePlayPauseButton();
				if(mediaPlayer.getCurrentTime().toSeconds() != 0) {
					mediaPlayer.play();
				} else {
					playMusic();
				}
			} else {
				isPlaying_bool = false;
				changePlayPauseButton();
				mediaPlayer.pause();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				current_song_time = song_progress_sl.getValue()/100 * mediaPlayer.getStopTime().toSeconds();
				mediaPlayer.setStartTime(Duration.seconds(current_song_time));
			}
		}
	}
	
	public void pressSuchen() {
        ArrayList<Song> searchedSongs = new ArrayList<Song>();
        if(filter.getText().equals(titel_str)) {
            searchedSongs = playlist.searchName(suche_tf.getText().toString());
        }
        else if(filter.getText().equals(interpret_str)) {
            searchedSongs = playlist.searchInterpret(suche_tf.getText().toString());
        }
        else if(filter.getText().equals(genre_str)) {
            searchedSongs = playlist.searchGenre(suche_tf.getText().toString());
        }
        fillSongTabelle(searchedSongs);
        current_songs_songs_tb = searchedSongs;
    }
	
	public void onClickRow() {
        current_song = songs_tb.getSelectionModel().getSelectedItem();
		unten_pn.getChildren().remove(gray_img_rec);
		if(isPlaying_bool) {
            mediaPlayer.stop();
        }
        if(current_song != null) {
            playMusic();
            isPlaying_bool = true;
            changePlayPauseButton();
            resetTimer();
            currentTitel_l.setText(current_song.getName());
            currentInterpret_l.setText(current_song.getInterpret());
            currentGenre_l.setText(current_song.getGenre());
            
            Image song_img = null;
            
            if(current_song.getImage() == null) {
            	 gray_img_rec.setFill(Color.BLACK);
            } else {
            	try {
				 song_img= new Image(new FileInputStream(current_song.getImage()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	            gray_img_rec.setFill(new ImagePattern(song_img));
            }
            	unten_pn.getChildren().addAll(gray_img_rec);
            
    		  
        }
    }
	
	public void changePlayPauseButton() {
		if(isPlaying_bool) {
			try {
				inputStream_button = new FileInputStream("images/pause_icon.png");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			pause = new Image(inputStream_button);
			pause_view = new ImageView(pause);
			pause_view.setFitHeight(25);
			pause_view.setFitWidth(25);
			play_bt.setGraphic(pause_view);

		} else {
			try {
				inputStream_button = new FileInputStream("images/play_icon.png");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			play = new Image(inputStream_button);
			play_view = new ImageView(play);
			play_view.setFitHeight(25);
			play_view.setFitWidth(25);
			play_bt.setGraphic(play_view);
		}
	}
	
	public void pressNextButton() {
		if(songs_tb.getSelectionModel().getSelectedIndex()+1 == songs_tb.getItems().size()) {
			songs_tb.getSelectionModel().select(0);
			current_song = songs_tb.getSelectionModel().getSelectedItem();
		} else {
			songs_tb.getSelectionModel().selectNext();
			current_song = songs_tb.getSelectionModel().getSelectedItem();
		}
		onClickRow();
		if(isRunning_bool) {
			 resetTimer();
		}
	}
	
	public void pressPreviousButton() {
		if(songs_tb.getSelectionModel().getSelectedIndex() == 0) {
			songs_tb.getSelectionModel().select(songs_tb.getItems().size()-1);
			current_song = songs_tb.getSelectionModel().getSelectedItem();
		} else {
			songs_tb.getSelectionModel().selectPrevious();
			current_song = songs_tb.getSelectionModel().getSelectedItem();
		}
		onClickRow();
		if(isRunning_bool) {
			resetTimer();
		}
	}
	
	public void playMusic() {
		media = new Media(current_song.getFile().toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		beginTimer();
		mediaPlayer.play();
	}
	
	public void stopMusic() {
		if(isPlaying_bool) {
			mediaPlayer.stop();
		}
		try {
			controller.saveAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			controller.savePlaylists();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void beginTimer() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				
				Platform.runLater(() -> {
					isRunning_bool = true;
					double currentTime = mediaPlayer.getCurrentTime().toSeconds();
					double endTime = media.getDuration().toSeconds();
					current_song_time = currentTime;
					if(!song_progress_sl.isPressed()) {
						song_progress_sl.setValue(currentTime/endTime*100);
					}
					
					
					int current_time_seconds01 = (int) Math.round(Math.floor((currentTime)%10));
					int current_time_seconds10 = (int) Math.round(Math.floor((currentTime)%60/10));
					int current_time_minutes = (int) Math.round(Math.floor(currentTime/60));
					current_time_l.setText(current_time_minutes + ":" + current_time_seconds10 + current_time_seconds01);
					int end_time_seconds01 = (int) Math.round(Math.floor((endTime)%10));
					int end_time_seconds10 = (int) Math.round(Math.floor((endTime)%60/10));
					int end_time_minutes = (int) Math.round(Math.floor(endTime/60));
					end_time_l.setText(end_time_minutes + ":" + end_time_seconds10 + end_time_seconds01);
					
					
					if(currentTime/endTime == 1) {
						stopTimer();
						pressNextButton();
					}
				
				});
			}
			
		};
		
		timer.scheduleAtFixedRate(task,1000,1000);
	}
	
	public void stopTimer() {
		isRunning_bool = false;
		timer.cancel();
	}
	
	public void resetTimer() {
		songProgressBar.setProgress(0);
		song_progress_sl.setValue(0);
	}
	
	public void songSliderMoved() {
		pressPlayPause();
		pressPlayPause();
	}
}