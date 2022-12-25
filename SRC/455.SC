;**
;**	Logics for room 455 -- Patti's face
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 12, 1989
;**


(script#				455)

(include	"face.sh")

(public
	rm455				0
)


;(define	vPattiFace					455)


(synonyms
	(woman patti )
)


(enum												;**	RoomScript state values
	intro
	readyToDoIt
	goodbye
	outtaHere
)


(instance rm455 of Room
	(properties
		picture		455
	)

	(method (init)
		(super init)

		(self
			setRegions		regBigFace,
			setLocales		locGirls,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		104 72)
		(Notify regBigFace EyeEast		133 60)
		(Notify regBigFace EyelidWest	104 69)
		(Notify regBigFace EyelidEast	135 55)
		(Notify regBigFace Nose			133 82)
		(Notify regBigFace Mouth		138 91)
	)
)


(instance RoomScript of Script
	(method (handleEvent event &tmp theObj [string 200])
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said '(go<out), date/patti')
					(Said 'take/anyword/home')
					(Said 'ask/anyword/date')
					(Said 'ask, get/date')
					(Said 'make/love, date')
					(Said 'lets<make/love, date')
					(Said 'anyword/make/love')
					(Said 'go/room, penthouse, place, comic, club, date, walk, bar, show, dance'))
				(cond
					((not (InRoom iDivorce))
						(PrintL "\"Hello, beautiful, my name is Larry; Larry Laffer.
							Why don't you and I get together some time very soon?\"")
						(PrintP "\"Hello yourself, 
							Mr. `Pretend-To- Be-A-Swinging-Single!'
							I never date married men when
							there are so many single guys just waiting their turn!\"")
						(React rFrown)
					)
					((not egoIsHunk)
						(PrintL "\"Patti, you know I'm divorced now.
							What do you say we get together for a little nightcap
							after your last set?\"")
						(PrintP "\"I'm sorry, Larry,
							but you must improve your poor physical condition.
							I could never go out with a man with such a pot belly!\"")
						(React rFrown)
					)
					((TstFlg needsShower)
						(PrintL "\"Patti, look at my new body!
							I'm a hunk!!
							Now, can we go out?\"")
						(PrintP "\"Phew! Don't you ever take a shower?
							I could never go out with a man with such B.O.\"")
						(React rFrown)
					)
					((TstFlg needsSoap)
						(PrintL "\"Patti, look at my new body!
							I'm a hunk!!
							Now, can we go out?\"")
						(PrintP "\"Phew! Don't you ever use soap?
							I could never go out with a man with such B.O.\"")
						(React rFrown)
					)
					((TstFlg needsDeodorant)
						(PrintL "\"Patti, look at my new body!
							I'm a hunk!!
							Now, can we go out?\"")
						(PrintP "\"Phew! Don't you ever use deodorant?
							I could never go out with a man with such B.O.\"")
						(React rFrown)
					)
					((not (InRoom iOrchids))
						(PrintL "\"Patti, please! 
							Let's try it. You and me. Together. Alone at last!
							What do you say?\"")
						(PrintP "\"I'd say `YES,' Larry, but where I come from
							a gentleman customarily gives a gift to his lady
							on their first date?
							I could never go out with a man who didn't care enough
							to give me presents.\"")
					)
					(else
						(self changeState readyToDoIt)
					)
				)
			)

			((or	(Said '/wedding')
					(Said 'propose/'))
				(Print "A little fast, aren't you, Larry?
					Why, you hardly know her!")
			)

			((Said 'fuck')
				(PrintP "\"Why, Larry! Shame on you!\"")
				(React rFrown 4)
			)

			((Said '/shoulder')
				(Print "She IS beautiful, isn't she?")
			)

			((Said '/penthouse, room')
				(PrintP "\"Yes, Larry, I DO live in the penthouse here.\"")
			)

			((Said 'feel')
				(PrintP "\"I might enjoy that later...\"")
				(React rSmile 5)
			)

			((Said 'kiss')
				(PrintP "\"You've never experienced that until you've had me...\"")
				(React rSmile 5)
			)

			((Said '/music, play')
				(PrintL "\"What talented fingers you have, Patti.\"")
				(PrintP "\"All the better to play with, Larry!\"")
				(React rSmile 5)
			)

			((Said 'shit')
				(PrintP "\"This is no time to get discouraged, Larry...\"")
				(React rFrown 4)
			)

			((Said 'talk')
				(switch (Random 1 3)
					(1
						(PrintP "\"You know, Larry, I always say
							it's not the men in your life that count_--
							it's the life in your men!\"")
					)
					(2
						(PrintP "\"Larry, I believe in the old saying:
							a good man is hard to find...
							but a hard man is good to find!\"")
					)
					(else
						(PrintP "\"Larry,
							you won't get much out of life if you don't
							`go for it.'\"")
					)
				)
			)

			((Said 'show/prick')
				(PrintL "\"Bet you've never seen one like this before!\"")
				(PrintP "\"See what, Larry?\"")
				(React rSmile 20)
			)

			((Said 'make/joke')
				(Print "You make a lame joke.")
				(Print "(You succeed.)" LOW)
				(PrintP "\"Hey, Larry! This is what I think of that joke!\"")
				(React rSilly 22)
			)

			((Said 'love/you, patti')
				(PrintL "\"Baby, I thinks I loves ya!\"")
				(PrintP "\"I'm sure you would!\"")
				(React rLove 44)
			)

			((Said '/ticket, showroom')
				(PrintL "\"Patti, do you have any idea how a guy like me
					could get into that showroom across the casino?\"")
				(PrintP "\"I don't know, Larry. They gave me a free pass.\"")
			)

			((Said 'take, play/song, request')
				(PrintL "\"Patti, do you take requests?\"")
				(PrintP "\"Certainly, Larry, but not when you are staring into
					my eyes like this.
					You make me want to play this song (and this song only!)\"")
			)

			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'look/bar, room')
					(Said 'quit, leave, exit, quit, walk, go')
					(Said 'leave, rise, (stand<up), (get<off, up)/stool'))
				(self changeState goodbye)
			)

			((Said 'look>')
				(cond
					((Said '/leg')
						(PrintP "\"Oh, Larry!\" smiles Patti.
							\"I love it when you look at me like that!\"")
						(React rSexy 22)
					)

					((Said '/bracelet')
						(Print "Her pearly white wrist passes through
							an expensive golden bracelet.")
					)

					((Said '/ear')
						(Print "Tasteful imported Italian earrings grace
							her delicate tasteful ears.")
					)

					((Said '/tit')
						(Print "\"My, what I'd love to do with those!\"
							you whisper to yourself.")
						(PrintP "\"Oh, Larry!\" smiles Patti overhearing you.
							\"You're so subtle!\"")
						(React rOh 12)
					)

					((Said '/keyboard')
						(Print "Patti's grand piano stretches across the bar 
							like a wino with a 6-dollar gallon of wine!")
					)

					((Said '[/piano, woman, patti]')
						(Print "Before you sits the latest girl of your dreams
							the world-famous piano bar entertainer, 
							Passionate Patti.")
						(PrintP "\"Hello,\" she says with a smile.")
						(React rSmile 33)
					)
				)
			)

			((Said 'give, throw, put, show, drop>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What did you want to give her?")
					)
					((not theObj)
						(Print "Where did you get that?")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((TstFlg needsShower)
						(PrintP "\"Phew! Don't you ever take a shower?
							I could never go out with a man with such B.O.\"")
						(React rFrown)
					)
					((TstFlg needsSoap)
						(PrintP "\"Phew! Don't you ever use soap?
							I could never go out with a man with such B.O.\"")
						(React rFrown)
					)
					((== theObj (inventory at iCreditCard))
						(PrintL "\"Do you take plastic?\"")
						(PrintP "\"Honey, I'm not interested in credit,
							(except the way they announce my act!).
							If you want to give me a tip,
							put it in the tip glass right here on the piano.
							And be sure I don't hear any clinking sounds!\"")
						(React rFrown)
					)
					((== theObj (inventory at iGrass))
						(if (== (theObj view) ivGrassSkirt)
							(PrintL "\"Patti, this grass skirt would look
								perfect on you.\"")
							(PrintP "\"If it's not imported,	Larry,
								there's no way you're going to get me to wear it!\"")
						else
							(PrintL "\"Patti, would you care for some of my grass?\"")
							(PrintP "\"Not me, Larry. I don't do drugs!\"")
						)
						(React rFrown)
					)
					((== theObj (inventory at iSoap))
						(PrintL "\"Patti, how about this bar of soap?
							Would you like to have it? 
							It's yours.\"")
						(PrintP "\"Gosh, Larry, you really have a way with women,
							don't you?\"")
						(React rFrown)
					)
					((== theObj (inventory at iMoney))
						(PrintL (Format @string "\"What do you say, baby?
							Would %d dollars make you interested in me?\"" dollars))
						(PrintP "\"How dare you!\"")
						(React rFrown)
					)
					((== theObj (inventory at iDeed))
						(PrintL "\"Take my land. Please!\"")
						(PrintP "\"What are you? Henny Youngman?\"")
						(React rFrown)
					)
					((== theObj (inventory at iTowel))
						(PrintL "\"Would you like to rub this all over your
							naked body?\"")
						(PrintP "\"No, Larry, but I do enjoy being rubbed!\"")
						(React rFrown)
					)
					((== theObj (inventory at iKeycard))
						(PrintL "\"Would you like to work out over at Fat City?
							You could take my keycard.\"")
						(PrintP "\"Hardly. I can get into Fat City whenever I
							wish. You see, I `know' the clerk there!\"")
						(React rSmile)
					)
					((== theObj (inventory at iDivorce))
						(PrintL "\"Patti, look! 
							I did it! 
							My divorce is finalized.
							Here, take it.
							Frame this thing and mount it here on your piano!\"")
						(if (not (ego has iKeycard))
							(Print "As you hand your hard-earned divorce decree
								to Patti, something flutters to the floor.")
							(ego get iKeycard)
							(SetFlg missedKeycardPoints)
							(Print "\"Well, well,\" you say, \"look at this.
								Somehow Suzi Cheatem's Fat City Membership Card 
								got mixed in with these papers.
								I suppose I could always return it to her.\"")
						)
						(theGame changeScore 100)
						(PutInRoom iDivorce)
						(PrintP "\"Congratulations, Larry.
							Did I mention earlier how much I love single men?
							Suddenly, you've become a lot more interesting to me.
							We should get together some time.\"")
						(React rSmile)
					)
					((== theObj (inventory at iOrchids))
						(cond
							((and (== curTimer ORCHIDS)
									(== rgTimer 0))	;**	Picked 'em too long ago.
								(PrintL "\"Patti, here.
									Take these wilted, old flowers.\"")
								(PrintP "\"No way, Larry.
									Although I have a soft spot in my heart for orchids,
									I could never accept these tired old things.
									Is that all you think of me?
									Dead flowers, indeed!\"")
								(React rFrown)
;**									(= curTimer FALSE)
							)
							((not (InRoom iDivorce))
								(PrintL "\"Patti, please accept these flowers and 
									let's get together tonight when you get off work.\"")
								(PrintP "\"No, Larry, not now.
									Although I have a soft spot in my heart for orchids 
									and I am growing more fond of you every moment
									we're together,
									I could never date a married man!\"")
								(React rFrown)
							)
							((not (== (theObj view) ivLei))
								(PrintL "\"Patti, although their beauty pales besides
									yours, I'd like you to accept these orchids
									as a humble token of my interest in you.\"")
								(PrintP "\"Oh, Larry.
									Your flowers are very pretty, 
									but what woman would want to go out with a 
									man who is unable to `lei' her?\"")								
								(React rFrown)
							)	
							(else
								(PrintL "\"Patti, please wear my lei...
									tonight... for me... alone!\"")
								(PrintP "\"Oh, Larry, thank you!
									You're such a charmer!
									I'm ready. What do you want to do?\"")
								(theGame changeScore 100)
								(PutInRoom iOrchids)
								(React rSmile)
							)
						)
					)
					(else
						(PrintL (Format @string "\"Patti, look at this 
							`%s?'\"" (theObj name)))
						(PrintP "\"Why, Larry. How droll.\"")
					)
				)
				(event claimed TRUE)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(readyToDoIt
				(HandsOff)
				(PrintP "\"Oh, well; what the hell.
					It's getting late, 
					I'm ready to quit for the night,
					and you're looking better all the time.
					Sure, let's go!!\"")
				(theGame changeScore 100)
				(= egoState DOING_IT)
				(++ state)						;**	skip next state
				(React rSmile 99)
				(= cycles 33)
			)

			(goodbye
				(PrintL "\"I'm leaving now, Patti\"")
				(PrintP "\"See you later, Larry!\"")
				(React rSmile 55)
				(= cycles 33)
			)

			(outtaHere
				(curRoom newRoom 450)
			)
		)
	)
)

