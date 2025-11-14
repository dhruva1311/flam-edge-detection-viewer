// Frame statistics interface
interface FrameStats {
    fps: number;
    resolution: { width: number; height: number };
    processingTime: number;
}

class EdgeDetectionViewer {
    private canvas: HTMLCanvasElement;
    private ctx: CanvasRenderingContext2D;
    private fpsElement: HTMLElement;
    private resolutionElement: HTMLElement;
    private processTimeElement: HTMLElement;

    constructor() {
        this.canvas = document.getElementById('frame-canvas') as HTMLCanvasElement;
        this.ctx = this.canvas.getContext('2d')!;
        this.fpsElement = document.getElementById('fps')!;
        this.resolutionElement = document.getElementById('resolution')!;
        this.processTimeElement = document.getElementById('process-time')!;

        this.initialize();
    }

    private initialize(): void {
        console.log('Edge Detection Viewer initialized');
        this.loadSampleFrame();
        this.updateStats({
            fps: 15,
            resolution: { width: 640, height: 480 },
            processingTime: 33
        });
    }

    private loadSampleFrame(): void {
        // Create a sample gradient for demo
        this.canvas.width = 640;
        this.canvas.height = 480;
        
        const gradient = this.ctx.createLinearGradient(0, 0, this.canvas.width, this.canvas.height);
        gradient.addColorStop(0, '#667eea');
        gradient.addColorStop(1, '#764ba2');
        
        this.ctx.fillStyle = gradient;
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        
        this.ctx.fillStyle = 'white';
        this.ctx.font = '24px Arial';
        this.ctx.textAlign = 'center';
        this.ctx.fillText('Sample Frame - Replace with processed image', 
                         this.canvas.width / 2, 
                         this.canvas.height / 2);
    }

    public updateStats(stats: FrameStats): void {
        this.fpsElement.textContent = stats.fps.toString();
        this.resolutionElement.textContent = `${stats.resolution.width}x${stats.resolution.height}`;
        this.processTimeElement.textContent = `${stats.processingTime}ms`;
    }

    public displayFrame(imageData: ImageData): void {
        this.canvas.width = imageData.width;
        this.canvas.height = imageData.height;
        this.ctx.putImageData(imageData, 0, 0);
    }
}

// Initialize viewer when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    const viewer = new EdgeDetectionViewer();
    console.log('Viewer ready');
});