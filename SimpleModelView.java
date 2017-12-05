package com.src.wavefront.obj;

import javax.swing.JFrame;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SimpleModelView  extends JFrame{
	// INSTANCE ****************************************************************


/** The canvas where the object is rendered. */
private Canvas3D canvas;

/** Simplifies the configuration of the scene. */
private SimpleUniverse universe;

/** The root node of the scene. */
private BranchGroup root;

// INITIALIZATION **********************************************************

/**
 * Creates a window with a 3D canvas on its center.
 *
 * @throws IOException if some error occur while loading the model
 */
public SimpleModelView() throws IOException {
    configureWindow();
    //configureCanvas();
    conigureUniverse();
    addModelToUniverse();
    addLightsToUniverse();
    root.compile();
    universe.addBranchGraph(root);
}

/**
 * Defines basic properties of this window.
 */
private void configureWindow() {
    setTitle("Basic Java3D Program");
    setSize(640, 480);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int locationX = (screenSize.width - getWidth()) / 2;
    int locationY = (screenSize.height - getHeight()) / 2;
    setLocation(locationX,locationY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

/**
 * Defines basic properties of the canvas. 
 */
private void configureCanvas() {
    canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas.setDoubleBufferEnable(true);
    getContentPane().add(canvas, BorderLayout.CENTER);
}

/** 
 * Defines basic properties of the universe.
 */
private void conigureUniverse() {
    universe = new SimpleUniverse(canvas);
    universe.getViewingPlatform().setNominalViewingTransform();
}

/**
 * Loads a model from disk and assign the root node of the scene
 *
 * @throws IOException if it's impossible to find the 3D model
 */
private void addModelToUniverse() throws IOException {
    Scene scene = getSceneFromFile("ROACH_mod.obj"); 
    root = scene.getSceneGroup();
}

/** 
 * Adds a dramatic blue light... 
 */
private void addLightsToUniverse() {
    Bounds influenceRegion = new BoundingSphere();
    Color3f lightColor = new Color3f(Color.BLUE);
    Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);
    DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
    light.setInfluencingBounds(influenceRegion);
    root.addChild(light);
}

// ACCESS ******************************************************************

/**
 * Loads a scene from a Wavefront .obj model.
 *
 * @param location the path of the model
 * @return Scene the scene contained on the model
 * @throws IOException if some error occur while loading the model
 */
public static Scene getSceneFromFile(String location) throws IOException {
    ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
    return file.load(new FileReader("/Users/meethunpanda/Downloads/dodecahedron.obj"));
}

// MAIN ********************************************************************

public static void main(String[] args) {
    try {
        new SimpleModelView().setVisible(true);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
}
