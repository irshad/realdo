<script>
    import { createEventDispatcher, onMount } from "svelte";
    import Modal from "../../utils/Modal.svelte";
	import PincodeInput from 'pincode-input';

    export let password = '';
    let savedPassword;
    let show = false;
    const dispatch = createEventDispatcher();

    onMount(() => {
        savedPassword = localStorage.getItem('pin');
		new PincodeInput('#input-value', {
			count: 4,
			secure: true,
			previewDuration: 200,
			onInput: (value) => {
                password = value;
			}
		});
    });

    const submit = () => {
        if (atob(savedPassword) == password) {
            dispatch('goto');
        } else {
            alert('Wrong PIN');
        }
    };

    const fogotPin = () => {
        show=!show;
    };

    const deleteButton = () => {
        localStorage.clear();
        window.location.reload();
    };
</script>

<div class="main-section">
    <div>
        <form class="block-section" on:submit|preventDefault={submit}>
            <h1>Unlock with your PIN</h1>
            <div class="pin-section">
                <div id="input-value"></div>
            </div>
            <button type="submit" class="jump-in br-12">
                Jump In
            </button>
        </form>
        <button class="forgot-pin br-8" on:click={fogotPin}>
            Fogot Pin
        </button>
    </div>

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

{#if show}
    <Modal>
        <h4>
            All your data will <br> be removed?
        </h4>

        <div class="btn-section">
            <button class="cancel-btn br-8" on:click={() => show=!show}>
                Cancel
            </button>
            <button class="delete-btn br-8" on:click={deleteButton}>
                Delete
            </button>
        </div>
    </Modal>
{/if}

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

    .forgot-pin {
        width: fit-content;
        font-size: 12px;
        padding: 4px 8px;
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
        float: right;
        margin-top: 16px;
        border: 2px solid var(--dark-color);
        background-color: var(--danger-color);
        cursor: pointer;
    }

    .forgot-pin:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    h2 {
        bottom: 16px;
        position: absolute;
        text-align: center;
        font-size: 21px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    h2 span {
        display: none;
        font-size: 12px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    a {
        color: var(--dark-color);
    }

    h4 {
        margin: 0 0 24px 0;
        text-align: center;
        font-size: 16px;
        font-weight: 700;
        color: var(--dark-color);
        font-family: 'Roboto Mono', monospace;
    }

    .btn-section {
        width: 100%;
        gap: 16px;
        display: flex;
        justify-content: center;
    }

    .btn-section button {
        width: 100%;
        height: 32px;
        border: 2px solid var(--dark-color);
        padding: 0;
        cursor: pointer;
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    .cancel-btn {
        background-color: var(--success-color);
    }

    .delete-btn {
        background-color: var(--danger-color);
    }

    .cancel-btn:hover, .delete-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }
</style>