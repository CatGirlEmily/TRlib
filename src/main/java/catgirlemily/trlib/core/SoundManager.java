package catgirlemily.trlib.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager - Handles audio playback and caching.
 */
public class SoundManager {
	// Cache to avoid reloading the same file from disk multiple times
	private static final Map<String, AudioData> soundCache = new HashMap<>();

	/**
	 * Internal class to store pre-loaded audio data.
	 */
	private record AudioData(byte[] buffer, AudioFormat format, long length) {}

	/**
	 * Plays a sound once or in a loop.
	 * @param path   Path to the .wav file.
	 * @param looped Whether the sound should repeat.
	 */
	public static void play(String path, boolean looped) {
		new Thread(() -> {
			try {
				Clip clip = loadClip(path);
				if (clip == null) return;

				if (looped) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}

				clip.start();

				// Listener to close the line when sound finishes (prevents memory leaks)
				clip.addLineListener(event -> {
					if (event.getType() == LineEvent.Type.STOP && !looped) {
						clip.close();
					}
				});

			} catch (Exception e) {
				System.err.println("[SoundManager] Error: " + e.getMessage());
			}
		}).start();
	}

	/**
	 * Loads a clip into memory.
	 */
	private static Clip loadClip(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			System.err.println("[SoundManager] File not found: " + path);
			return null;
		}

		AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(stream);
		return clip;
	}

	/**
	 * Clears all cached sounds (call this on game exit).
	 */
	public static void cleanup() {
		soundCache.clear();
	}
}
