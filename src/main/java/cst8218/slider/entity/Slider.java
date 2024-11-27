/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.slider.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author User
 */
@Entity
public class Slider implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // Constants
    public static final int INITIAL_SIZE = 50; // default size in pixels
    public static final int MAX_TRAVEL_LIMIT = 100;
    public static final int X_LIMIT = 800; // x position limit
    public static final int Y_LIMIT = 600; // y position limit
    public static final int SIZE_LIMIT = 200;
    public static final int MAX_DIR_CHANGES = 10;
    public static final int DECREASE_RATE = 1;
    public static final int TRAVEL_SPEED = 5;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(1)
    @Max(SIZE_LIMIT)
    private Integer size = INITIAL_SIZE;

    @NotNull
    @Min(0)
    @Max(X_LIMIT)
    private Integer x;

    @NotNull
    @Min(0)
    @Max(Y_LIMIT)
    private Integer y;

    @NotNull
    @Min(1)
    @Max(MAX_TRAVEL_LIMIT)
    private Integer maxTravel;

    @NotNull
    private Integer currentTravel = 0;

    @NotNull
    private Integer mvtDirection = 1; // 1 for right, -1 for left

    @NotNull
    private Integer dirChangeCount = 0;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(Integer maxTravel) {
        this.maxTravel = maxTravel;
    }

    public Integer getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Integer currentTravel) {
        this.currentTravel = currentTravel;
    }

    public Integer getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(Integer mvtDirection) {
        this.mvtDirection = mvtDirection;
    }

    public Integer getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(Integer dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slider)) {
            return false;
        }
        Slider other = (Slider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.ved.slider.entity.Slider[ id=" + id + " ]";
    }
    /**
    * Updates the properties to simulate the passing of one unit of time.
    * This method simulates the slider's movement by updating its travel distance,
    * direction, and other properties based on game mechanics.
    */
    public void timeStep() {
        // Check if the slider is still allowed to move (i.e., maxTravel > 0)
        if (maxTravel > 0) {

            // Update the currentTravel by adding or subtracting based on the movement direction.
            // mvtDirection is either 1 (moving right) or -1 (moving left).
            currentTravel += mvtDirection * TRAVEL_SPEED;

            // Check if the slider has reached or exceeded its max travel distance (in either direction)
            if (Math.abs(currentTravel) >= maxTravel) {

                // Reverse the direction of movement when maxTravel is reached.
                // If it was moving right (1), it will now move left (-1), and vice versa.
                mvtDirection = -mvtDirection;

                // Increment the count of how many times the slider has changed direction.
                dirChangeCount++;

                // Check if the slider has changed direction more times than the allowed limit (MAX_DIR_CHANGES).
                if (dirChangeCount > MAX_DIR_CHANGES) {

                    // Decrease the maxTravel distance by a constant DECREASE_RATE (to simulate decay of movement).
                    maxTravel -= DECREASE_RATE;

                    // Reset the direction change count to 0 after reducing maxTravel.
                    // This ensures we start counting direction changes for the new reduced maxTravel.
                    dirChangeCount = 0;
                }
            }
        }
    }
   
    public void updateWithNonNullValues(Slider newSlider) {
        if (newSlider.getSize() != null) {
            this.setSize(newSlider.getSize());
        }
        if (newSlider.getX() != null) {
            this.setX(newSlider.getX());
        }
        if (newSlider.getY() != null) {
            this.setY(newSlider.getY());
        }
        if (newSlider.getCurrentTravel() != null) {
            this.setCurrentTravel(newSlider.getCurrentTravel());
        }
        if (newSlider.getMaxTravel() != null) {
            this.setMaxTravel(newSlider.getMaxTravel());
        }
        if (newSlider.getMvtDirection() != null) {
            this.setMvtDirection(newSlider.getMvtDirection());
        }
        if (newSlider.getDirChangeCount() != null) {
            this.setDirChangeCount(newSlider.getDirChangeCount());
        }
    }


}
