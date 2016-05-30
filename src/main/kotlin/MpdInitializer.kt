package mpd

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import java.io.File
import java.util.*

val path = "build/music"
val templateSound: String = "/silence.ogg"
val artistNumber = 50
val albumNumber = 5
val trackNumber = 10

data class Tag(val tagName: FieldKey, val tagValue: String)

fun getID(): String {
    return UUID.randomUUID().toString().subSequence(0, 8) as String
}

class MpdInitializer(rootFolderPath: String) {

    val rootFolder = File(rootFolderPath)

    init {
        if (!rootFolder.isDirectory) {
            rootFolder.mkdir()
        }
    }

    fun addSong(vararg tags: Tag) {
        val targetFile = File(rootFolder, getID() + ".ogg")
        File(javaClass.getResource(templateSound).toURI()).copyTo(targetFile)
        val f = AudioFileIO.read(targetFile)
        val tag = f.getTag()
        tags.forEach {
            tag.setField(it.tagName, it.tagValue);
        }
        f.commit();
    }

    fun addSongs() {
        for (artist in 1..artistNumber) {
            val artistId = getID()
            for (album in 1..albumNumber) {
                val albumId = getID()
                for (track in 1..trackNumber) {
                    addSong(Tag(FieldKey.ARTIST, "Artist-" + artistId),
                            Tag(FieldKey.ALBUM_ARTIST, "AlbumArtist-" + artistId),
                            Tag(FieldKey.ALBUM, "Album-" + albumId),
                            Tag(FieldKey.TITLE, "Title-" + albumId + "-" + track),
                            Tag(FieldKey.TRACK, track.toString()),
                            Tag(FieldKey.YEAR, "2016"),
                            Tag(FieldKey.GENRE, "Jazz"),
                            Tag(FieldKey.COMPOSER, "Composer-" + artistId),
                            Tag(FieldKey.CONDUCTOR, "Conductor-" + artistId),
                            Tag(FieldKey.DISC_NO, "1"),
                            Tag(FieldKey.COMMENT, "This is a sample comment for id : " + albumId))
                }
            }
        }
        println((artistNumber * albumNumber * trackNumber).toString() + " sample tracks have been generated.")
    }

}


fun main(args: Array<String>) {
    File(path).deleteRecursively()
    val mpdInitializer = MpdInitializer(path)
    mpdInitializer.addSongs()
}