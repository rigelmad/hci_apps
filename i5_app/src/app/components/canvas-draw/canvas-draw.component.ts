import { Component, ViewChild, Renderer } from '@angular/core';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'canvas-draw',
  templateUrl: 'canvas-draw.component.html'
})
export class CanvasDraw {

    @ViewChild('myCanvas', { static : false } ) canvas: any;

    canvasElement: any;
    lastX: number;
    lastY: number;

    currentColour: string = '#1abc9c';
    availableColours: any;

    brushSize: number = 10;

    currentShape: string = "line";

    constructor(public platform: Platform, public renderer: Renderer) {
        console.log('Hello CanvasDraw Component');

        this.availableColours = [
          '#1abc9c',
          '#3498db',
          '#9b59b6',
          '#e67e22',
          '#e74c3c'
        ];
    }

    ngAfterViewInit(){

        this.canvasElement = this.canvas.nativeElement;

        this.renderer.setElementAttribute(this.canvasElement, 'width', this.platform.width() + '');
        this.renderer.setElementAttribute(this.canvasElement, 'height', this.platform.height() + '');

    }

    changeColour(colour){
      if (colour == '#fff') { // If eraser
        this.currentShape = "line";
      }
      this.currentColour = colour;
    }

    changeSize(size){
        this.brushSize = size;
    }

    changeShape(shape) {
      this.currentShape = shape;
    }

    handleStart(ev){
        console.log(ev);
        this.lastX = ev.touches[0].pageX;
        this.lastY = ev.touches[0].pageY;
    }

    handleMove(ev){
        let ctx = this.canvasElement.getContext('2d');
        let currentX = ev.touches[0].pageX;
        let currentY = ev.touches[0].pageY;

        ctx.strokeStyle = this.currentColour;
        ctx.lineWidth = this.brushSize;

        if (this.currentShape == 'line') {
          ctx.beginPath();
          ctx.lineJoin = "round";
          ctx.moveTo(this.lastX, this.lastY);
          ctx.lineTo(currentX, currentY);
          ctx.closePath();
          ctx.stroke();
          this.lastX = currentX;
          this.lastY = currentY;
        }
    }

    // Helper for handleEnd
    dist(currentX, currentY, lastX, lastY) {
      let a = currentX - lastX;
      let b = currentY - lastY;
      let distance = Math.sqrt(a*a + b*b);
      return distance;
    }

    handleEnd(ev) {
      console.log(ev);
      let ctx = this.canvasElement.getContext('2d');
      let currentX = ev.changedTouches[0].pageX;
      let currentY = ev.changedTouches[0].pageY;

      ctx.fillStyle = this.currentColour;

      if (this.currentShape == 'rect') {
        ctx.fillRect(this.lastX, this.lastY,
            currentX - this.lastX, currentY - this.lastY);
      } else if (this.currentShape == 'oval') {
        ctx.beginPath();
        ctx.ellipse(
          this.lastX,
          this.lastY,
          this.dist(currentX, currentY, this.lastX, this.lastY),
          this.dist(currentX, currentY, this.lastX, this.lastY),
          0, 0, 2 * Math.PI); // Start, end angles
        ctx.fill();
      }
    }

    clearCanvas() {
        let ctx = this.canvasElement.getContext('2d');
        ctx.clearRect(0, 0, this.canvasElement.width, this.canvasElement.height);
    }


}
