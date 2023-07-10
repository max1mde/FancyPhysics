<p align="center">
  <a href="https://github.com/MaximFiedler/FancyPhysics/blob/master/LICENSE"><img src="https://img.shields.io/github/license/MaximFiedler/FancyPhysics.svg" alt="License"></a>  
<a href="https://github.com/MaximFiedler/FancyPhysics/releases"><img src="https://img.shields.io/github/v/tag/MaximFiedler/FancyPhysics.svg" alt="Version"></a>  
<a href="https://jitpack.io/#MaximFiedler/FancyPhysics"><img src="https://jitpack.io/v/MaximFiedler/FancyPhysics.svg" alt="jitpack"></a>  
</p>


Implementation
------------

Gradle

	repositories {
		maven { url 'https://jitpack.io' }
	}

	dependencies {
	        implementation 'com.github.MaximFiedler:FancyPhysics:2.0.2'
	}
Maven

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

 	<dependency>
	    <groupId>com.github.MaximFiedler</groupId>
	    <artifactId>FancyPhysics</artifactId>
	    <version>2.0.2</version>
	</dependency>

-----

# Examples

### Events
```java
    @EventHandler
    public void onParticleSpawnEvent(ParticleSpawnEvent event) {
        // Changes the particle texture to a diamond block
        BlockDisplay display = event.getParticleDisplayList();
        display.setBlock(Material.DIAMOND_BLOCK.createBlockData());
    }
```
