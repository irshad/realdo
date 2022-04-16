<script>
    import { onMount } from "svelte";
    import { Pincode, PincodeInput } from "svelte-pincode";

    export let password = '';
    let input;

    onMount(() => {
		input.focus()
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
            <Pincode bind:value={password}>
                <PincodeInput bind:ref={input} />
                <PincodeInput />
                <PincodeInput />
                <PincodeInput />
              </Pincode>
        </div>
        <button class="jump-in">
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

    .pin-section {
        display: flex;
        justify-content: center;
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

    :global([data-pincode]) {
        border: none !important;
        display: flex;
        gap: 16px;
    }

    :global([data-pincode] input) {
        font-size: 24px !important;
        font-weight: 700;
        width: 44px !important;
        height: 44px !important;
        border: 2px solid var(--dark-color) !important;
        padding: 0 !important;
    }

    :global([data-pincode] input:focus) {
        outline: none !important;
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
        text-align: center;
        font-size: 24px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    h2 span {
        font-size: 18px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    a {
        color: var(--dark-color);
    }
</style>