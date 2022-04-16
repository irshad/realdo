<script>
	import { onMount } from "svelte";
	import SignIn from "./pages/auth/SignIn.svelte";
	import SignUp from "./pages/auth/SignUp.svelte";
	import Dashboard from "./pages/Dashboard.svelte";

	let landingPage = false;
	let dashboard = false;

	let user = localStorage.getItem("pin");

	onMount(() =>{
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

{#if dashboard}
	<Dashboard/>
{:else}
	{#if landingPage }
		<SignIn on:goto={openPage}/>
	{:else}
		<SignUp/>
	{/if}
{/if}

<style>

</style>