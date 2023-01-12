<a name="readme-top"></a>
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<br />
<div align="center">

<h3 align="center">KPN4J</h3>

  <p align="center">
    A lightweight framework for easy implementation of Kahn Process Networks in Java
    <br />
    <a href="https://sumeetk321.github.io/KPN4J/io/github/sumeetk321/package-summary.html"><strong>Javadocs</strong></a>
    <br />
  </p>
</div>



<!-- ABOUT KPN4J -->
# About KPN4J

KPN4J is a lightweight Java library for straightforward implementation of open Kahn Process Networks, a model of computation developed by French computer scientist Gilles Kahn. 

In general, the model consists of nodes, which carry abstract processes, and first-in-first-out channels. Each node performs some computation, or process, with any number of data "tokens" that the FIFO channels feed them. Multiple nodes can't write to a single FIFO channel, and a FIFO channel can't be used by multiple nodes. Writing to a channel always succeeds, while reading from a channel will stall if there are insufficient tokens to perform a node's computation. The number of tokens a node accepts from a certain channel and the number of tokens a node outputs to its output channel can be modified.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- INSTALLATION -->

## Installation


1. Clone the repository using ```git clone https://github.com/sumeetk321/KPN4J.git```
2. Load the project in your preferred IDE (e.g., Eclipse)
3. Export the project to a JAR file (File -> Export, in the case of Eclipse)
4. In your future Java projects, simply add the JAR file to your project's Classpath

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE-->
## Usage Example

Let's build the following barebones KPN:
<p align="center">
<img src="https://user-images.githubusercontent.com/18608410/211935774-7a0cdac8-6eaa-41bd-9724-3e42dc80f60d.png" width=75% height=75%>
</p>

The network has three process nodes: A, B, and C. For the sake of simplicity, let's make the process a simple addition; that is, each node will sum all of its inputs and output the sum at the end of each timestep. Nodes A and B take in two tokens from their respective channels, and they each output one token to their respective channels. Node C on the other hand takes one token from each of its two input channels, and outputs one token to its output channel. Let's give the network some example data, highlighted in purple, and see what happens after simulating for a few timesteps (remember, data is first-in-first-out, so the first inputs will be received by the next process node first):
<p align="center">
<img src="https://user-images.githubusercontent.com/18608410/211936322-7abeb042-47c9-44e3-8895-f695be73e1a3.png" width=75% height=75%>
<img src="https://user-images.githubusercontent.com/18608410/211936535-01f1db2b-e3fd-4850-838d-b8b166004dc1.png" width=75% height=75%>
<img src="https://user-images.githubusercontent.com/18608410/211936803-8f9e82ad-7c01-4a42-ae1b-36e253118af5.png" width=75% height=75%>
</p>


Our first step will be to create our own process node class. The ```ProcessNode``` class in KPN4J's library is abstract, meaning the user must define the actual process that each node performs. In our case, we're doing addition, in which each node just sums all of its inputs. Here's what such a node would look like in Java, implementing the abstract ```process``` method:

```java

import java.util.ArrayList;
import java.util.List;
import io.github.sumeetk321.*;
public class ExampleNode extends ProcessNode<Integer>{

	public ExampleNode(String name) {
		super(name);
	}

	public List<Integer> process(Object...params) {
		int sum = 0;
		for(int i = 0; i < params.length; i++) {
			sum+= (int) params[i];
		}
		List<Integer> out = new ArrayList<Integer>();
		out.add(sum);
		
		return out;
	}

}
```
The process method simply adds all of the numbers in the ```params``` array and returns the single value wrapped in a ```List<Integer>```. Now that we have implemented our process, let's build our KPN. In your main method, instantiate a new KPN object:

```java
KahnProcessNetwork<Integer> kpn = new KahnProcessNetwork<Integer>();
```

Then, instantiate your nodes (the constructor takes in the node's name) and add them to the network:

```java
ExampleNode a = new ExampleNode("A");
ExampleNode b = new ExampleNode("B");
ExampleNode c = new ExampleNode("C");

kpn.addProcessNode(a);
kpn.addProcessNode(b);
kpn.addProcessNode(c);
```

Next, add the FIFO channels from A to C and from B to C. The ```1,1``` parameters signify that this channel outputs one token and the destination node accepts one token as well. The same notation is used when instantiating FIFOChannel objects:

```java
kpn.addFIFOChannel(a, c, 1, 1);
kpn.addFIFOChannel(b, c, 1, 1);
```

Input FIFO channels must be created and wired separately:

```java
FIFOChannel<Integer> aChannel = new FIFOChannel<Integer>(1, 2);
a.addIncomingChannel(aChannel);

FIFOChannel<Integer> bChannel = new FIFOChannel<Integer>(1, 2);
b.addIncomingChannel(bChannel);
```

Let's push our data:

```java
aChannel.push(1);
aChannel.push(4);
		
bChannel.push(8);
bChannel.push(9);
```

To view the current state of the network, call the KPN's ```toString()``` method:
```java
System.out.println(kpn.toString());
```

The current output will be:

```
Outgoing from C: 
Outgoing from B: 
Outgoing from A: 
```

Since we haven't simulated anything yet, there is no output from any node's FIFO channel. Let's simulate a timestep:

```java
kpn.simulateTimestep();
System.out.println(kpn.toString());
```

Now, the output is:

```
Outgoing from C: 
Outgoing from B: 17
Outgoing from A: 5
```

This is consistent with our diagram above! Let's simulate another timestep:

```
Outgoing from C: 22
Outgoing from B: 
Outgoing from A: 
```

Congrats! You've made your first KPN using KPN4J. Keep in mind that if you construct your networks improperly, you can end up with *unbounded* FIFO channels that grow infinitely, or stalls that create backlogs in the network. 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- DOCUMENTATION -->
## Documentation

See the associated [GitHub Pages site](https://sumeetk321.github.io/KPN4J/io/github/sumeetk321/package-summary.html) to view the full Javadocs for each class and method in the project.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

I welcome pull requests!

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Sumeet Kulkarni - sumeetk2@illinois.edu

Project Link: [https://github.com/sumeetk321/KPN4J/blob/main/LICENSE](https://github.com/sumeetk321/KPN4J/blob/main/LICENSE)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/sumeetk321/KPN4J.svg?style=for-the-badge
[contributors-url]: https://github.com/sumeetk321/KPN4J/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/sumeetk321/KPN4J.svg?style=for-the-badge
[forks-url]: https://github.com/sumeetk321/KPN4J/network/members
[stars-shield]: https://img.shields.io/github/stars/sumeetk321/KPN4J.svg?style=for-the-badge
[stars-url]: https://github.com/sumeetk321/KPN4J/stargazers
[issues-shield]: https://img.shields.io/github/issues/sumeetk321/KPN4J.svg?style=for-the-badge
[issues-url]: https://github.com/sumeetk321/KPN4J/issues
[license-shield]: https://img.shields.io/github/license/sumeetk321/KPN4J.svg?style=for-the-badge
[license-url]: https://github.com/sumeetk321/KPN4J/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/sumeet-kulkarni-798181172/
