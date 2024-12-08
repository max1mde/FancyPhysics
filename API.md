<p align="center">
  <a href="https://github.com/max1mde/FancyPhysics/blob/master/LICENSE"><img src="https://img.shields.io/github/license/max1mde/FancyPhysics.svg" alt="License"></a>  
<a href="https://github.com/max1mde/FancyPhysics/releases"><img src="https://img.shields.io/github/v/tag/max1mde/FancyPhysics.svg" alt="Version"></a>  
<a href="https://jitpack.io/#max1mde/FancyPhysics"><img src="https://jitpack.io/v/max1mde/FancyPhysics.svg" alt="jitpack"></a>  
</p>


Implementation
------------

Gradle

	repositories {
		maven { url 'https://jitpack.io' }
	}

	dependencies {
	        implementation 'com.github.max1mde:FancyPhysics:2.6.1'
	}
Maven

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

 	<dependency>
	    <groupId>com.github.max1mde</groupId>
	    <artifactId>FancyPhysics</artifactId>
	    <version>2.6.1</version>
	</dependency>

-----

# Examples
--------------------------------------------------------------------
FancyPhysics instance
```java
API fancyPhysicsAPI = FancyPhysics.getAPI();
```
Particles
```java
fancyPhysicsAPI.getParticleGenerator().simulateBlockParticles(location, material);
fancyPhysicsAPI.getParticleGenerator().simulateBloodParticles(location, material);
fancyPhysicsAPI.getParticleGenerator().simulateSplashBloodParticles(location, material);
```
Config
```java
if(fancyPhysicsAPI.getConfig().isRealisticExplosion()) {
	// Do something
}
```
Particle list
```java
// For example remove all currently existing particles
for(BlockDisplay blockDisplay : fancyPhysicsAPI.getBlockDisplayList()) {
	blockDisplay.remove();
}
fancyPhysicsAPI.getBlockDisplayList().clear();
```
--------------------------------------------------------------------
### Events
ParticleSpawnEvent
```java
    @EventHandler
    public void onParticleSpawnEvent(ParticleSpawnEvent event) {
        // Changes the particle texture to a diamond block
        BlockDisplay display = event.getParticleDisplay();
        display.setBlock(Material.DIAMOND_BLOCK.createBlockData());
    }
```
ParticleEffectEvent
```java
    @EventHandler
    public void onParticleEffect(ParticleEffectEvent event) {
        // Do something with the list
        List<BlockDisplay> displayList = event.getParticleDisplayList();

        if(event.getLocation().getWorld().getName().equals("your_custom_world")) {
            // You can cancel every fancy physics event
            event.setCancelled(true);
        }
    }
```
TreeBreakEvent
```java
    @EventHandler
    public void onTreeBreak(TreeBreakEvent event) {
        // Cancel the normal animation
        event.setCancelled(true);
        Tree tree = event.getTree();
        
        // Play the instant break animation instead of the falling down animation
        tree.breakInstantWithParticles();
    }
```
--------------------------------------------------------------------
