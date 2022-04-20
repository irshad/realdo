<script>
	import { onMount } from "svelte";
	import SignIn from "./pages/auth/SignIn.svelte";
	import SignUp from "./pages/auth/SignUp.svelte";
	import Dashboard from "./pages/Dashboard.svelte";
	import SplashScreen from "./pages/SplashScreen.svelte";
	import Toast from "./utils/Toast.svelte";

	let landingPage = false;
	let dashboard = false;
	let splashscreen = true;
	let user = localStorage.getItem("pin");

	onMount(() =>{
		setTimeout(() => {
			splashscreen = false
		}, 1500);

		if(user) {
			landingPage = true;
		} else {
			landingPage = false;
		}
	});

	const openPage = () => {
		dashboard=!dashboard
	}
</script>

{#if splashscreen}
	<SplashScreen />
{:else}
	{#if dashboard}
		<Dashboard/>
	{:else}
		{#if landingPage }
			<SignIn on:goto={openPage}/>
		{:else}
			<SignUp/>
		{/if}
	{/if}
{/if}

<Toast />

<style>
	
</style>