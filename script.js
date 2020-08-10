"use strict";

try {
	var canvas = document.querySelector("canvas");
	var c = canvas.getContext("2d");

	canvas.width = 800;
	canvas.height = 450;

	var center = [canvas.width / 2, canvas.height / 2];

	// |x1 - x2| + |y1 - y2| + |z1 - z2| would be more stable
	function dist(x, y, z, x1, y1, z1) {
		return Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2) + Math.pow(z1 - z, 2));
	}
	
	function axisX(x,y,z,angleX,angleY,angleZ) {
            return (x*(Math.cos(angleX)*Math.cos(angleY))) + (y*((Math.cos(angleX)*Math.sin(angleY)*Math.sin(angleZ))-(Math.sin(angleX)*Math.cos(angleZ)))) + (z*((Math.cos(angleX)*Math.sin(angleY)*Math.cos(angleZ))+(Math.sin(angleX)*Math.sin(angleZ))));
        }
        function axisY(x,y,z,angleX,angleY,angleZ) {
            return (x*(Math.sin(angleX)*Math.cos(angleY))) + (y*((Math.sin(angleX)*Math.sin(angleY)*Math.sin(angleZ))+(Math.cos(angleX)*Math.cos(angleZ)))) + (z*((Math.sin(angleX)*Math.sin(angleY)*Math.cos(angleZ))-(Math.cos(angleX)*Math.sin(angleZ))));
        }
        function axisZ(x,y,z,angleX,angleY,angleZ) {
            return (x*(Math.sin(angleY)*-1)) + (y*(Math.cos(angleY)*Math.sin(angleZ))) + (z*(Math.cos(angleY)*Math.cos(angleZ)))
        }

	function Node(x, y, z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.renderMount = [];

		this.screenX = 0;
		this.screenY = 0;
	}

	function Mesh(nodes, name, x, y, z, startDist) {
		this.nodes = nodes;
		this.name = name;
		this.x = x;
		this.y = y * -1;
		this.z = z;
		this.rx = 0;
                this.ry = 0;
                this.rz = 0;
		this.startDist = startDist;

		for (var i = 0; i < this.nodes.length; i++) {
			for (var h = 0; h < this.nodes.length; h++) {
				if (this.nodes[i] !== this.nodes[h]) {
					if (dist(this.nodes[h].x, this.nodes[h].y, this.nodes[h].z, this.nodes[i].x, this.nodes[i].y, this.nodes[i].z) < this.startDist) {
						this.nodes[i].renderMount.push(this.nodes[h]);
					}
				}
			}
		}

		this.setScreenCartesian = function() {
			for (var i = 0; i < this.nodes.length; i++) {

				// let vector[x,y,z] be the amount of displacement
				//  relative to the node to position of camera
				
				// let node[x,y,z] be the camera relative cordinates of each node 
				
				// let camera[x,y,z] = [0,0,0] as this defines camera
				//  relative space instead of real space
				
				// let real space be an offset value matrix for camera relative space 
				// let the perspective scalar set how far away the plane each
				//  ray is coliding with is away from the camera point

				// essentially, coordinates will shift around the camera
				//  instead of the camera coordinates shifting around.
				//  this creates camera relative space and real space
				//  where real space is an offeset to camera space

				// define parametric equasion for the ray
				//  [x,y,z,x1,y1,z1] f(t) = [x + t(x1 - x),y + t(y1 - y),z + t(z1 - 1)] 
				
				// let x,y,z = node[x,y,z]
				// let x1,y1,z1 = camera[x,y,z]

				// let the camera plane normal (n) vector coordinates
				//  in camera relative space = [0,0,perspective]
				
				// let the test vector (w) = [0,0,perspective] + [x,y,z]

				// find t in f(t) with equasion
				//  -(w[0] * n[0] + w[1] * n[1] + w[2] * n[2]) / (n[0] * p(0)[0] + n[1] * p(0)[1] + n[0] * p(0)[2])
				
				// plug output of equasion into f(t) to get x,y cordinates
				// offset coridinates to fit canvas 

				function ray(x, y, z, x1, y1, z1, t, set) {
					switch (set) {
						case 0:
							return x + t * (x1 - x);
							break;
						case 1:
							return y + t * (y1 - y);
							break;
						case 2:
							return z + t * (z1 - z);
							break;
					}
				}

				var perspective = +document.querySelector("#persp").value;
				var rayVector = [];

				rayVector[0] = this.x + axisX(this.nodes[i].x,this.nodes[i].y,this.nodes[i].z,this.rx,this.ry,this.rz);
				rayVector[1] = this.y + axisY(this.nodes[i].x,this.nodes[i].y,this.nodes[i].z,this.rx,this.ry,this.rz);
				rayVector[2] = this.z + axisZ(this.nodes[i].x,this.nodes[i].y,this.nodes[i].z,this.rx,this.ry,this.rz);

				var cameraNormal = [];
				cameraNormal[0] = 0;
				cameraNormal[1] = 0;
				cameraNormal[2] = perspective;

				var testVector = [];
				testVector[0] = cameraNormal[0] - rayVector[0];
				testVector[1] = cameraNormal[1] - rayVector[1];
				testVector[2] = cameraNormal[2] * 10 - rayVector[2];

				var t = -(testVector[0] * cameraNormal[0] + testVector[1] * cameraNormal[1] + testVector[2] * cameraNormal[2]) / (cameraNormal[0] * ray(rayVector[0], rayVector[1], rayVector[2], 0, 0, 0, 0, 0) + cameraNormal[1] * ray(rayVector[0], rayVector[1], rayVector[2], 0, 0, 0, 0, 1) + cameraNormal[2] * ray(rayVector[0], rayVector[1], rayVector[2], 0, 0, 0, 0, 2));

				this.nodes[i].screenX = ray(rayVector[0], rayVector[1], rayVector[2], 0, 0, 0, t, 0) + center[0];
				this.nodes[i].screenY = ray(rayVector[0], rayVector[1], rayVector[2], 0, 0, 0, t, 1) + center[1];

			}
		}

		this.render = function() {
			this.setScreenCartesian();

			for (var i = 0; i < this.nodes.length; i++) {
				for (var h = 0; h < this.nodes.length; h++) {
					if (this.nodes[h] !== this.nodes[i]) {
						if (dist(this.nodes[i].x, this.nodes[i].y, this.nodes[i].z, this.nodes[h].x, this.nodes[h].y, this.nodes[h].z) < this.startDist) {
							c.beginPath()
							c.moveTo(this.nodes[i].screenX, this.nodes[i].screenY);
							c.lineTo(this.nodes[h].screenX, this.nodes[h].screenY);
							c.stroke();
						}
					}
				}
			}
		}
	}

	var globalMeshArr = [];

	var createMesh = function(gx, gy, gz, size, type, name, thresh) {
		var nodeMount = [];

		switch(type) {
			case "point":
				nodeMount.push(new Node(gx, gy, gz));
				break;

			case undefined:
				globalMeshArr.push(new Mesh(nodeMount, "unnamed", gx, gy, gz, thresh));
				break;

			case "cube": {
				nodeMount.push(new Node(size, size, (size * -1)))
				nodeMount.push(new Node((size * -1), size, (size * -1)));
				nodeMount.push(new Node(size, (size * -1), (size * -1)));
				nodeMount.push(new Node((size * -1), (size * -1), (size * -1)));

				nodeMount.push(new Node(size, size, size))
				nodeMount.push(new Node((size * -1), size, size));
				nodeMount.push(new Node(size, (size * -1), size));
				nodeMount.push(new Node((size * -1), (size * -1), size));

				break;
			}

			case "line": {
				nodeMount.push(new Node(gx, gy, 0));
				nodeMount.push(new Node(gx, gy, 10));
				nodeMount.push(new Node(gx, gy, 20));
				nodeMount.push(new Node(gx, gy, 30));
				nodeMount.push(new Node(gx, gy, 40));

				break;
			}
		}

		globalMeshArr.push(new Mesh(nodeMount, name, gx, gy, gz, thresh));
	}

	createMesh(0,0,40*10,40,"cube","cube1",90);

	var j = 0;

	function animate() {
		requestAnimationFrame(animate);
		c.clearRect(0, 0, innerWidth, innerHeight);

		j += .02;
		for (var o = 0; o < globalMeshArr.length; o++) {
			c.fillStyle = "black";
			globalMeshArr[o].ry += .02;
                    	globalMeshArr[o].rx += .02;
                    	globalMeshArr[o].rz += .02;
			globalMeshArr[o].render();
		}
	}

	animate();
} catch (err) {
	alert(err);
}

alert("compiled");
