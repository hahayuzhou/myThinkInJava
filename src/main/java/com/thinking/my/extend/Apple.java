package com.thinking.my.extend;

public class Apple extends BaseClass{
    private String colour;

    public Apple() {

    }

    private Apple(Builder builder){
        this.name = builder.Name;
        this.colour = builder.colour;
    }


    @Override
    public int getPageSize() {
        return 0;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "colour='" + colour + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder extends BaseBuilder {
        private String colour;

        private Builder(String colour) {
            this.colour = colour;
        }

        private Builder() {
        }

        public Builder colour(String colour){
            this.colour = colour;
            return this;
        }

        public Apple build () {
            return new Apple(this);
        }
    }
}
