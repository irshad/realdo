<script>
    import { onMount } from "svelte";
	import PincodeInput from 'pincode-input';

    export let password = '';

    onMount(() => {
		new PincodeInput('#input-value', {
			count: 4,
			secure: false,
			previewDuration: 200,
			onInput: (value) => {
                password = value;
			}
		})
    });

    const submit = () => {
        localStorage.setItem('pin', btoa(password));
        window.location.reload();
    }
</script>

<div class="main-section">
    <form class="block-section" on:submit|preventDefault={submit}>
        <h1>Create new PIN</h1>
        <div class="pin-section">
            <div id="input-value"></div>
        </div>
        <button class="jump-in br-12">
            Let's Go
        </button>
    </form>

    <h2>
        RealDO
        <br>
        <span>
            Made with ❤️ by 
            <a href="https://tecode.in" target="_blank">
                Irshad Ali
            </a>
        </span>
    </h2>
</div>

<style>
    .main-section {
        width: 100%;
        height: 100%;
        display: flex;
        flex-flow: column;
        align-items: center;
        justify-content: space-around;
        background-color: var(--primary-color);
    }

    h1 {
        margin: 0;
        font-size: 24px;
        padding-bottom: 24px;
        text-align: center;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    .jump-in {
        width: 100%;
        height: 44px;
        color: var(--dark-color);
        font-size: 18px;
        font-weight: 700;
        padding-top: 4px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 24px;
        border: 2px solid var(--dark-color);
        background-color: var(--success-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
        padding-bottom: 4px;
        cursor: pointer;
    }

    .jump-in:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    h2 {
        position: absolute;
        bottom: 16px;
        text-align: center;
        font-size: 21px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    h2 span {
        font-size: 12px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    a {
        color: var(--dark-color);
    }
</style>